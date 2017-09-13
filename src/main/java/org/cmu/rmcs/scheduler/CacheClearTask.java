package org.cmu.rmcs.scheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;









import org.cmu.rmcs.service.ModuleService;
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
    
    @Resource
    private ModuleService moduleServiceImp;
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
    @Scheduled(cron = "* * 2 * * ?")
    public void switchModuleRecordToDb(){
       //每天凌晨两点处理一次
       System.out.println("moduleRecord scheduler task working!");
       Map<String, ArrayList<String>> fMap=redisServiceImp.getFamilyAndItsNames();
       Iterator<Entry<String, ArrayList<String>>> iterator=fMap.entrySet().iterator();
       while(iterator.hasNext()){
           Entry<String, ArrayList<String>> entry=iterator.next();
           String family=entry.getKey();
           ArrayList<String> names = entry.getValue();
           for(String name:names){
               moduleServiceImp.insertModuleRecord(family, name);
               
           }
       }
      
    }
    
}
