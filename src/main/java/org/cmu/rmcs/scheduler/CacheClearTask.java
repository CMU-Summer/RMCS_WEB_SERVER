package org.cmu.rmcs.scheduler;

import java.util.Set;

import javax.annotation.Resource;



import org.cmu.rmcs.service.RedisService;
import org.cmu.rmcs.util.ContantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheClearTask {

    @Resource
    private RedisService redisServiceImp;
    
    private static Logger LOGGER =LoggerFactory.getLogger(CacheClearTask.class);
    
    
    @Scheduled(cron = "0 0/30 * * * ?")
    public void clearCache(){
         //每小时触发一次:任意时刻的0 分 0 秒
       System.out.println("scheduler task working!");
       LOGGER.debug("scheduler task working!");
       Set<String> groupNames= redisServiceImp.getGroupNamesFromCache();
           for(String s : groupNames){
               long len=redisServiceImp.getSetOrListSize(s+ContantUtil.POSTFIX_GROUP_FEEDBACK_KEY, ContantUtil.LEN_TYPE_LIST);//列表的长度
               if(len>ContantUtil.MAX_FEEDBACK_LENTH){
                   //超过最大条数
                  boolean opt= redisServiceImp.clearGroupFeeback(s, ContantUtil.MAX_FEEDBACK_LENTH);//保留最大的
                  if(!opt)LOGGER.error("feedback clear failed!");
               }
           }
    }
    
    
}
