package org.cmu.rmcs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.cmu.rmcs.pojo.GroupfeedbackCustomStruct;
import org.cmu.rmcs.pojo.WS_fd_cmd;
import org.cmu.rmcs.pojo.WS_feedback_group;
import org.cmu.rmcs.util.ContantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;
@Service
public class FeedbackSocketService implements Runnable{
    private static Map<String, Long> groupIndexMapLocal = new HashMap<String, Long> ();
    private static Map<String, Long>groupLenthMap = new HashMap<String, Long>();
    private static Map<String, Boolean> groupNeedGetMap = new HashMap<String, Boolean>();
    private static WebSocketSession sessionLocal = null;
    private static Logger logger = LoggerFactory
            .getLogger(FeedbackSocketService.class);
    @Resource
    private RedisService redisServiceImp;
    
    public FeedbackSocketService(){
        
      
        
    }
    public void setRedisService(RedisService redisService) {
        redisServiceImp=redisService;
    }
    public void setSession(WebSocketSession session){
      if(sessionLocal==null){
          sessionLocal=session;
          
      }
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
      while (true) {
      
      System.out.println("session.isOpen():"+sessionLocal.isOpen());
      if(sessionLocal.isOpen()==false){
          
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
              sessionLocal.sendMessage(tx);
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
   

    private void analysisAndUpdateIndexMap(Set<String> list) {
        // 根据当前缓存的group的set，更新map索引
        Map<String, Long> feedbackListMap = groupIndexMapLocal;
        for (String s : list) {
            long len = redisServiceImp.getSetOrListSize(s
                    + ContantUtil.POSTFIX_GROUP_FEEDBACK_KEY,
                    ContantUtil.LEN_TYPE_LIST);
                    groupLenthMap.put(s, len);//吧新长度放group的长度map里面
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
                    groupNeedGetMap.put(s, true);//在标志map里面放一下，之后更新判断
                }
                
                
            }

        }
    }

    private Map<String, List<GroupfeedbackCustomStruct>> getNewsetFeedbackListForEachGroup() {
        Map<String, Long> map = groupIndexMapLocal;
        Map<String, Long> lenMap = groupLenthMap;
        Map<String, List<GroupfeedbackCustomStruct>> fdMap = new HashMap<>();
        Iterator<Entry<String, Long>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            // 取里面的每个group的名字和索引
            Entry<String, Long> entry = iterator.next();
            // 取出来了
            //如果这个的group的feedback是零的话，别取了,还要设置一下索引也为0
            if(lenMap.containsKey(entry.getKey())){
                if(lenMap.get(entry.getKey()) == 0){
                    groupIndexMapLocal.put(entry.getKey(), (long) 0);
                    continue;
                }
                
            }
            
            //如果长度不是0,那么看看索引的情况
            if(lenMap.containsKey(entry.getKey())){
                long recordIndex=map.get(entry.getKey());
                long length=lenMap.get(entry.getKey());
                if(recordIndex==length-1){
                    //就比长度小1
                    if(groupNeedGetMap.containsKey(entry.getKey()) == false){
                        //还没有收到重置，意味着没有变化
                        //什么也别做
                        System.out.println("no new feed!");
                        continue;
                    }
                }else if(recordIndex>length-1){
                    //上天了
                    groupIndexMapLocal.put(entry.getKey(), length-1);
                    continue;
                }
            }
            
            List<GroupfeedbackCustomStruct> theGroupFeedBackList = redisServiceImp
                    .getGroupFdList(entry.getKey(), entry.getValue());
            // 放到Map里面
            //只有list.size大于0的时候，采用put
            if(theGroupFeedBackList.size()>0)fdMap.put(entry.getKey(), theGroupFeedBackList);
            //更新索引map的数据，置成len-1
            groupIndexMapLocal.put(entry.getKey(), lenMap.get(entry.getKey())-1);
        }
        //清空标志map
        groupNeedGetMap.clear();
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
