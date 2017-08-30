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
         //每半小时触发一次
       System.out.println("scheduler task working!");
       LOGGER.error("scheduler task working!");
       Set<String> groupNames= redisServiceImp.getGroupNamesFromCache();
       //遍历每一个group
           for(String s : groupNames){
              //直接删掉
               if(redisServiceImp.deleteGroupFeedback(s)==false){
                   LOGGER.error("delete group fd error");
               }
           }
    }
    
    
}
