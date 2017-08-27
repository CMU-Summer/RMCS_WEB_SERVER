package org.cmu.rmcs.websocket.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.cmu.rmcs.pojo.GroupfeedbackCustomStruct;
import org.cmu.rmcs.pojo.WS_fd_cmd;
import org.cmu.rmcs.pojo.WS_feedback_group;
import org.cmu.rmcs.service.RedisService;
import org.cmu.rmcs.util.ContantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSONObject;

public class WebSocketFeedbackHandler extends TextWebSocketHandler {
    private static ThreadLocal<Map<String, Long>> groupIndexMapLocal = new ThreadLocal<>();
    private static ThreadLocal<Map<String, Long>> groupLenthMap = new ThreadLocal<>();
    private static ThreadLocal<Map<String, Boolean>> groupNeedGetMap = new ThreadLocal<>();
    private static Logger logger = LoggerFactory
            .getLogger(WebSocketFeedbackHandler.class);
    @Resource
    private RedisService redisServiceImp;

    @Override
    protected void handleTextMessage(WebSocketSession session,
            TextMessage message) throws Exception {

        super.handleTextMessage(session, message);
    }

    // 连接建立后
    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        if (groupIndexMapLocal.get() == null)
            groupIndexMapLocal.set(new HashMap<String, Long>());
        if(groupLenthMap.get()==null){
            groupLenthMap.set(new HashMap<String, Long>());
            
        }
        if(groupNeedGetMap.get() == null){
            groupNeedGetMap.set(new HashMap<String,Boolean>());
            
        }
        while (true) {
            if(session.isOpen()==false){
             
                System.out.println("feedback  sock is closed");
                return;
                 
            }
            Set<String> groupList = redisServiceImp.getGroupNamesFromCache();
            // 分析/更新索引值
            analysisAndUpdateIndexMap(groupList);
            // 取每个group的最新fd
            Map<String, List<GroupfeedbackCustomStruct>> newFdListMap = getNewsetFeedbackListForEachGroup();
            // 转换格式
            List<WS_feedback_group> wsList = changeGroupFdStructToWsFdStruct(newFdListMap);
            if (wsList.size() > 0) {
                // 有最新的groupfeedback,进行打包，然后转换成字符串
                String cmdStr = packageWSfeedbackCmdAndToString(wsList);
                try {
                    TextMessage tx = new TextMessage(cmdStr);
                    // 发送
                    session.sendMessage(tx);
                } catch (Exception e) {
                    // TODO: handle exception
                    logger.error("fd cmd send error:" + e.getMessage());
                    e.printStackTrace();
                }

            }

            try {
                Thread.sleep(ContantUtil.THREAD_SLEEP_TIME);
            } catch (Exception e) {
                // TODO: handle exception
                logger.error("thread sleep error:" + e.getMessage());
            }
            System.out.println("next fd... ");
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

    private void analysisAndUpdateIndexMap(Set<String> list) {
        // 根据当前缓存的group的set，更新map索引
        Map<String, Long> feedbackListMap = groupIndexMapLocal.get();
        for (String s : list) {
            long len = redisServiceImp.getSetOrListSize(s
                    + ContantUtil.POSTFIX_GROUP_FEEDBACK_KEY,
                    ContantUtil.LEN_TYPE_LIST);
                    groupLenthMap.get().put(s, len);//吧新长度放group的长度map里面
            if (feedbackListMap.containsKey(s) == false) {
                // 这个key没出现过
                feedbackListMap.put(s, new Long(0));// 从0开始取
                //把这个放到
             
            } else {
                // 出现过,如果下标小于长度，就设置取的长度为siez-1
                // 取一下这个group的feedback长度;
                long len1 = feedbackListMap.get(s);
                if (len <= len1 && len !=0 && len1!=0) {
                    // 数组变化了,总长度比索引还小当然变化了，索引重置为最后的,而且都不为0
                    feedbackListMap.put(s, len - 1);
                    groupNeedGetMap.get().put(s, true);//在标志map里面放一下，之后更新判断
                }
                
                
            }

        }
    }

    private Map<String, List<GroupfeedbackCustomStruct>> getNewsetFeedbackListForEachGroup() {
        Map<String, Long> map = groupIndexMapLocal.get();
        Map<String, Long> lenMap = groupLenthMap.get();
        Map<String, List<GroupfeedbackCustomStruct>> fdMap = new HashMap<>();
        Iterator<Entry<String, Long>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            // 取里面的每个group的名字和索引
            Entry<String, Long> entry = iterator.next();
            // 取出来了
            //如果这个的group的feedback是零的话，别取了,还要设置一下索引也为0
            if(lenMap.containsKey(entry.getKey())){
                if(lenMap.get(entry.getKey()) == 0){
                    groupIndexMapLocal.get().put(entry.getKey(), (long) 0);
                    continue;
                }
                
            }
            
            //如果长度不是0,那么看看索引的情况
            if(lenMap.containsKey(entry.getKey())){
                long recordIndex=map.get(entry.getKey());
                long length=lenMap.get(entry.getKey());
                if(recordIndex==length-1){
                    //就比长度小1
                    if(groupNeedGetMap.get().containsKey(entry.getKey()) == false){
                        //还没有收到重置，意味着没有变化
                        //什么也别做
                        System.out.println("no new feed!");
                        continue;
                    }
                }else if(recordIndex>length-1){
                    //上天了
                    groupIndexMapLocal.get().put(entry.getKey(), length-1);
                    continue;
                }
            }
            
            List<GroupfeedbackCustomStruct> theGroupFeedBackList = redisServiceImp
                    .getGroupFdList(entry.getKey(), entry.getValue());
            // 放到Map里面
            //只有list.size大于0的时候，采用put
            if(theGroupFeedBackList.size()>0)fdMap.put(entry.getKey(), theGroupFeedBackList);
            //更新索引map的数据，置成len-1
            groupIndexMapLocal.get().put(entry.getKey(), lenMap.get(entry.getKey())-1);
        }
        //清空标志map
        groupNeedGetMap.get().clear();
        return fdMap;// 返回这个map

    }

    private List<WS_feedback_group> changeGroupFdStructToWsFdStruct(
            Map<String, List<GroupfeedbackCustomStruct>> mapList) {
        // 转变结构
        List<WS_feedback_group> ws_feedback_groups = new ArrayList<WS_feedback_group>();
        Iterator<Entry<String, List<GroupfeedbackCustomStruct>>> iterator = mapList
                .entrySet().iterator();
        while (iterator.hasNext()) {
            // 对每个的group的feedbacklist都进行打包
            Entry<String, List<GroupfeedbackCustomStruct>> entry = iterator
                    .next();
            if(entry.getValue().size() == 0){continue;}//没有新的group
            WS_feedback_group wGroup = new WS_feedback_group();
            wGroup.packageGroupFeedback(entry.getValue());// 打包好
            ws_feedback_groups.add(wGroup);// 放进去

        }
        return ws_feedback_groups;

    }

    private String packageWSfeedbackCmdAndToString(
            List<WS_feedback_group> wsglist) {
        WS_fd_cmd ws_fd_cmd = new WS_fd_cmd();
        ws_fd_cmd.packageFd_cmd(wsglist);
        return JSONObject.toJSONString(ws_fd_cmd);

    }

}
