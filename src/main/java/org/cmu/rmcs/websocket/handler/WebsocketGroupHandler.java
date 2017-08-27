package org.cmu.rmcs.websocket.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.cmu.rmcs.pojo.FamilyStruct;
import org.cmu.rmcs.pojo.GroupStruct;
import org.cmu.rmcs.pojo.NameStruct;
import org.cmu.rmcs.pojo.WS_group_info;
import org.cmu.rmcs.pojo.WS_group_sock_cmd;
import org.cmu.rmcs.service.RedisService;
import org.cmu.rmcs.util.ContantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSONObject;

public class WebsocketGroupHandler extends TextWebSocketHandler {
    // 这个地方用来group信息变化的情况
    //
    private static Logger logger = LoggerFactory
            .getLogger(WebSocketFeedbackHandler.class);
    private static final ThreadLocal<WebSocketSession> sessionLocal = new ThreadLocal<>();
    private static final ThreadLocal<HashMap<String, GroupStruct>> gList = new ThreadLocal<HashMap<String, GroupStruct>>(); // 保存成结构的
    private static final ThreadLocal<Set<String>> groupNameSet = new ThreadLocal<Set<String>>(); // 上次取出来的groupSet

    @Resource
    private RedisService redisServiceImp;

    // 接收文本消息，并发送出去

    @Override
    protected void handleTextMessage(WebSocketSession session,
            TextMessage message) throws Exception {
      
        super.handleTextMessage(session, message);
    }

   

    // 连接建立后处理
    // 这里面要处理的信息就是
    // 先抽取redis里面的group
    // 然后对比该链接的group list，
    // 多了发送group add 命令
    // 少了发送 group delete 命令
    //

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        System.out.println("connect to the  group websocket success......");

        // 初始化线程变量
        if (sessionLocal.get() == null)
            sessionLocal.set(session);
        if (gList.get() == null)
            gList.set(new HashMap<String, GroupStruct>());
        if (groupNameSet.get() == null)
            groupNameSet.set(new HashSet<String>());
        // 以循环的方式去处理
        while (true) {
            if(session.isOpen()==false){
                System.out.println("group sock is closed");
                return;
                
            }
            WS_group_sock_cmd ws_group_sock_cmd = new WS_group_sock_cmd();
             groupNumsTaskWork(ws_group_sock_cmd);// group的数目
             List<GroupStruct> cacheList =this.redisServiceImp.getGroupStrInCache();
            groupStateTaskWork(ws_group_sock_cmd, cacheList);// group的状态
            // 现在cmd已经打包完成，就是发送出去了
           
            try {
                if(ws_group_sock_cmd.getAddList().size()>0 ||
                   ws_group_sock_cmd.getDeleteList().size()>0 ||
                   ws_group_sock_cmd.getStateList().size()>0
                        
                        ){
                    //只要有一个就可以发送出去了
                    String cmd_jsonStr = JSONObject.toJSONString(ws_group_sock_cmd);
                    TextMessage textMessage = new TextMessage(cmd_jsonStr);
                    session.sendMessage(textMessage); //吧命令发送出去
                }
                
                //否则就让线程休眠，等待下一次循环
               
            } catch (Exception e) {
                // TODO: handle exception
                logger.error("send message error "+e.getMessage());
                e.printStackTrace();

            }
            try {
                Thread.sleep(ContantUtil.THREAD_SLEEP_TIME);
            } catch (Exception e) {
                // TODO: handle exception
                logger.error("thread sleep error "+e.getMessage());
                e.printStackTrace();
            }
            System.out.println("next....");
        }

    }

    // 抛出异常时处理
    @Override
    public void handleTransportError(WebSocketSession session,
            Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        logger.debug("websocket chat connection closed......");

    }

    // 连接关闭后处理
    @Override
    public void afterConnectionClosed(WebSocketSession session,
            CloseStatus closeStatus) throws Exception {
        logger.debug("websocket chat connection closed......");

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private List<GroupStruct> groupNumsTaskWork(
            WS_group_sock_cmd ws_group_sock_cmd) {
        // 这里处理group的增加 减少 并发送不同的命令
        Set<String> addSet = new HashSet<>();
        Set<String> deleteSet = new HashSet<>();
        analysisGroupNumsFromCache(addSet, deleteSet);
        List<GroupStruct> groupStructs = this.redisServiceImp
                .getSomeGroupFromCache(addSet);
        if (addSet.size() > 0) {
            // 这些group都是需要发送的
            List<WS_group_info> group_infos = new ArrayList<>();
            for (GroupStruct g : groupStructs) {
                WS_group_info group_info = new WS_group_info();
                group_info.parseGroupStruct(g);// 转变成前端可识别的格式
                group_infos.add(group_info);// 加进来

            }
            // 还需要添加到咱们的map里面
            putOrDeleteNewMapInLocalMap(null, groupStructs,
                    ContantUtil.GROUP_MAP_PUT);// 放的方式更新map
            //傻逼，还要放到咱们的set里面
            updateGroupSet(groupStructs,null,ContantUtil.SET_UPDATE_ADD);
            // 打包
            ws_group_sock_cmd.packageCmd(ContantUtil.SIGN_PACKAGE_GROUP_ADD,
                    group_infos); // 这个命令就已经打包好了

        }
        if (deleteSet.size() > 0) {
            // 有要删除的
            List<String> deleteList = new ArrayList<>();
            deleteList.addAll(deleteSet);// 这个list是要删除的
            // 在咱们map里面删除
            putOrDeleteNewMapInLocalMap(deleteSet, null,
                    ContantUtil.GROUP_MAP_DELETE);// 删的方式更新map
            //在咱们set里面删除
            updateGroupSet(null,deleteSet,ContantUtil.SET_UPDATE_DELETE);
            // 打包
            ws_group_sock_cmd.packageCmd(ContantUtil.SIGN_PACKAGE_GROUP_DEC,
                    deleteList);// 打包list

        }
        return groupStructs;
    }

    private void groupStateTaskWork(WS_group_sock_cmd ws_group_sock_cmd,
            List<GroupStruct> cacheList) {
        // 对group里面的信息进行更新
        List<GroupStruct> groupStructs = new ArrayList<>();
        analysisGroupStateFromCache(cacheList, groupStructs);
        if (groupStructs.size() > 0) {
            // 有改变过的group
            List<WS_group_info> group_infos = new ArrayList<>();
            for (GroupStruct g : groupStructs) {
                WS_group_info group_info = new WS_group_info();
                group_info.parseGroupStruct(g);// 转变成前端可识别的格式
                group_infos.add(group_info);// 加进来
            }
            // 对我们的map进行一下更新

            putOrDeleteNewMapInLocalMap(null, groupStructs,
                    ContantUtil.GROUP_MAP_PUT);// 冲掉之前的键
            // 打包
            ws_group_sock_cmd.packageCmd(ContantUtil.SIGN_PACKAGE_GROUP_STATE,
                    group_infos);

        }

    }

    private void analysisGroupNumsFromCache(Set<String> groupSetadd,
            Set<String> groupSetdelete) {
        // 分析当前线程里面的group的set和服务器的set有什么不一样
        Set<String> thisThreadGroupSet = groupNameSet.get();
        Set<String> cacheGroupSet = this.redisServiceImp.getGroupNamesFromCache();// 这个set是服务器最新的
        // 现在要对比这两个set得出结论
        // 先分析谁没有了：
        // 再分析谁多出来了:
        for (String s : cacheGroupSet) {
            if (thisThreadGroupSet.contains(s)) {
                // cache里面有，当前线程里面也有
                continue;
            } else {
                // cache有，当前线程没有
                groupSetadd.add(s);// 添加进去
            }

        }
        for (String s : thisThreadGroupSet) {
            if (cacheGroupSet.contains(s)) {
                // 当前线程里面有，cache里面有
                continue;
            } else {
                // 当前线程有,cache里面没有
                groupSetdelete.add(s);// 添加进去
            }
        }

    }

    private void analysisGroupStateFromCache(List<GroupStruct> cacheList,
            List<GroupStruct> changedList) {
        // 这里面抽出需要更新的groupStruct
        for (GroupStruct gs : cacheList) {
            // 每一个进行查找，进过上面的add delete，保证了到这里的数目是一只的
            if (gList.get().containsKey(gs.getName())) {
                // 包含，然后对比下
                boolean isChanged = isStateChange(
                        gList.get().get(gs.getName()), gs);
                if (isChanged) {
                    // 真的变过
                    changedList.add(gs);// 把这个结构加进去
                }
            }
        }

    }

    public boolean isStateChange(GroupStruct oldG, GroupStruct newG) {
        // 两个结构的状态是否一致
        for (int i = 0; i < oldG.getFamilyList().size(); i++) {
            FamilyStruct fold = oldG.getFamilyList().get(i);
            FamilyStruct fNew = newG.getFamilyList().get(i);
            for (int j = 0; j < fold.getNameList().size(); j++) {
                NameStruct nOld = fold.getNameList().get(j);
                NameStruct nNew = fNew.getNameList().get(j);
                if (nOld.getConnected() != nNew.getConnected()) {
                    // 不一样了
                    return true;// 改变过

                }

            }
        }
        return false;// 都到这里了，说明没有改变过
    }
    private void updateGroupSet(List<GroupStruct> gList,Set<String> nameStrings,int type){
        if(type == ContantUtil.SET_UPDATE_ADD){
            //加到集合里面去
            for(GroupStruct s:gList){
                groupNameSet.get().add(s.getName());
            }
            
        }else if(type == ContantUtil.SET_UPDATE_DELETE){
            //从集合中删除
            for(String s:nameStrings){
                groupNameSet.get().remove(s);
            }
            
        }
        
        
        
        
    }
    private void putOrDeleteNewMapInLocalMap(Set<String> strList,
            List<GroupStruct> groupStructList, int type) {
        // 更新咱们的map,
        if (type == ContantUtil.GROUP_MAP_PUT) {
            // 往进来放，groupStruct的不是null
            for (GroupStruct gS : groupStructList) {
                gList.get().put(gS.getName(), gS);// 都放进去
            }
        } else if (type == ContantUtil.GROUP_MAP_DELETE) {
            // 要删除，第一个List不是null
            for (String gName : strList) {
                gList.get().remove(gName);// 删除key对应的
            }
        }

    }
}