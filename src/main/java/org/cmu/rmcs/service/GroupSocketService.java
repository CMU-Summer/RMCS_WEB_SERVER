package org.cmu.rmcs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.cmu.rmcs.pojo.FamilyStruct;
import org.cmu.rmcs.pojo.GroupStruct;
import org.cmu.rmcs.pojo.NameStruct;
import org.cmu.rmcs.pojo.WS_group_info;
import org.cmu.rmcs.pojo.WS_group_sock_cmd;
import org.cmu.rmcs.util.ContantUtil;
import org.cmu.rmcs.websocket.handler.WebSocketFeedbackHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;
@Service
public class GroupSocketService implements Runnable{
    private static Logger logger = LoggerFactory
            .getLogger(GroupSocketService.class);
    private   WebSocketSession sessionLocal ;
    private   HashMap<String, GroupStruct> gList = new HashMap<String, GroupStruct>(); // 保存成结构的
    private   Set<String> groupNameSet = new HashSet<String>(); // 上次取出来的groupSet

    @Resource
    private RedisService redisServiceImp;
    
    public GroupSocketService(){
        // 初始化线程变量
    
       
        
        
    }
    public void setSession(WebSocketSession session){
        if(sessionLocal==null){
            sessionLocal=session;
            
        }
      }
    public void setRedisService(RedisService redisService) {
        redisServiceImp=redisService;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        // 以循环的方式去处理
      while (true) {
      
          System.out.println("gp session's id:"+sessionLocal.getId()+" session.isOpen():"+sessionLocal.isOpen());
          if(sessionLocal.isOpen()==false){
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
                  sessionLocal.sendMessage(textMessage); //吧命令发送出去
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
    
    private List<GroupStruct> groupNumsTaskWork(
            WS_group_sock_cmd ws_group_sock_cmd) {
        // 这里处理group的增加 减少 并发送不同的命令
        Set<String> addSet = new HashSet<>();
        Set<String> deleteSet = new HashSet<>();
        Set<String> fixedGroupSet =new HashSet<>();
        Set<String> deleteFixedGroupSet =new HashSet<>();
        analysisGroupNumsFromCache(addSet, deleteSet,fixedGroupSet,deleteFixedGroupSet);
        List<GroupStruct> groupStructs = this.redisServiceImp
                .getSomeGroupFromCache(addSet);
        
        List<WS_group_info> group_infos = new ArrayList<>();
        if (addSet.size() > 0) {
            // 这些group都是需要发送的
           
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

        }
       
        //打包
        ws_group_sock_cmd.packageCmd(ContantUtil.SIGN_PACKAGE_GROUP_ADD,
                group_infos); // 这个命令就已经打包好了
        //``````-----
        List<String> deleteList = new ArrayList<>();
      
        if (deleteSet.size() > 0) {
            // 有要删除的
            deleteList.addAll(deleteSet);// 这个list是要删除的
            // 在咱们map里面删除
            putOrDeleteNewMapInLocalMap(deleteSet, null,
                    ContantUtil.GROUP_MAP_DELETE);// 删的方式更新map
            //在咱们set里面删除
            updateGroupSet(null,deleteSet,ContantUtil.SET_UPDATE_DELETE);
         
        }
//        if(deleteFixedGroupSet.size()>0){
//            // 有要删除的
//            deleteList.addAll(deleteFixedGroupSet);// 这个list是要删除的
//            // 在咱们map里面删除
//            putOrDeleteNewMapInLocalMap(deleteFixedGroupSet, null,
//                    ContantUtil.GROUP_MAP_DELETE);// 删的方式更新map
//            //在咱们set里面删除
//            updateFixedGroupSet(null,deleteFixedGroupSet,ContantUtil.SET_UPDATE_DELETE);
//        }
        // 打包
        ws_group_sock_cmd.packageCmd(ContantUtil.SIGN_PACKAGE_GROUP_DEC,
                deleteList);// 打包list
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
            Set<String> groupSetdelete,Set<String> addfixedGroupSet,Set<String> deleteFixedGroupSet) {
        // 分析当前线程里面的group的set和服务器的set有什么不一样
        Set<String> thisThreadGroupSet = groupNameSet;
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
        //这里加上hack代码
        //取出_fix结尾的group，遍历，加入到thisThreadGroupSet里面去-----------------
//        for (String s : cacheFixedGroupSet) {
//            if (fixedGroupNameSet.contains(s)) {
//                // 当前线程有 
//                continue;
//            } else {
//                //当前线程没有,redis 有
//                addfixedGroupSet.add(s);// 添加进去
//            }
//
//        }
//        for (String s : fixedGroupNameSet) {
//            if (cacheFixedGroupSet.contains(s)) {
//                // 当前线程有 / redis 有
//                continue;
//            } else {
//                //当前线程 有，redis没有
//                deleteFixedGroupSet.add(s);// 添加进去
//            }
//
//        }
        //-----------------------------
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
            if (gList.containsKey(gs.getName())) {
                // 包含，然后对比下
                boolean isChanged = isStateChange(
                        gList.get(gs.getName()), gs);
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
                groupNameSet.add(s.getName());
            }
            
        }else if(type == ContantUtil.SET_UPDATE_DELETE){
            //从集合中删除
            for(String s:nameStrings){
                groupNameSet.remove(s);
            }
            
        }
        
        
        
        
    }
  
    
    private void putOrDeleteNewMapInLocalMap(Set<String> strList,
            List<GroupStruct> groupStructList, int type) {
        // 更新咱们的map,
        if (type == ContantUtil.GROUP_MAP_PUT) {
            // 往进来放，groupStruct的不是null
            for (GroupStruct gS : groupStructList) {
                gList.put(gS.getName(), gS);// 都放进去
            }
        } else if (type == ContantUtil.GROUP_MAP_DELETE) {
            // 要删除，第一个List不是null
            for (String gName : strList) {
                gList.remove(gName);// 删除key对应的
            }
        }

    }


}
