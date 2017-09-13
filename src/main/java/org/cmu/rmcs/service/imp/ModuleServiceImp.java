package org.cmu.rmcs.service.imp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cmu.rmcs.dao.ModuleDao;
import org.cmu.rmcs.dao.RedisDao;
import org.cmu.rmcs.pojo.GroupStruct;
import org.cmu.rmcs.pojo.ModuleInfo;
import org.cmu.rmcs.pojo.ModuleRecord;
import org.cmu.rmcs.pojo.ModuleRecord_cache;
import org.cmu.rmcs.pojo.Module_total_time;
import org.cmu.rmcs.pojo.Module_use_record;
import org.cmu.rmcs.service.ModuleService;
import org.cmu.rmcs.util.ContantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
@Service
public class ModuleServiceImp implements ModuleService{
    @Resource
    private RedisDao redisDao;
    @Resource
    private ModuleDao moduleDao;
    @Resource
    private RedisServiceImp redisServiceImp;
    
    private static Logger  LOGGER=LoggerFactory.getLogger(ModuleServiceImp.class);
    @Override
    public String getOneModuleTotalTimeString(String family, String name) {
        // TODO Auto-generated method stub
        //获得一个格式化的字符串 xxday xxhr xxmin xxsec

       Module_total_time m_t_t= moduleDao.getModule_total_time(family, name);
       long time=0;
       if(m_t_t!=null)time+=m_t_t.getTotal_time_second();
       time+=redisServiceImp.countModuleTimeInCache(family, name);
       return ContantUtil.formatTime(time);
    }

    @Override
    public long getOneModuleTotalTimeInt(String family, String name) {
        // TODO Auto-generated method stub
        Module_total_time m_t_t= moduleDao.getModule_total_time(family, name);
        long time=0;
        if(m_t_t!=null)time+=m_t_t.getTotal_time_second();
        time+=redisServiceImp.countModuleTimeInCache(family, name);
        return time;
    }

    @Override
    public List<ModuleRecord> getModuleRecordsLatest(String family, String name) {
        // TODO Auto-generated method stub
        //从缓存里面拿记录的字符串格式的数据
        
        
        return redisServiceImp.getModuleRecordLatest(family, name);
    }

    @Override
    public int getModuleTotalTimeRecord(String family, String name) {
        // TODO Auto-generated method stub
        //取数目，下一个函数需要用到
        return moduleDao.getModule_total_time_RecordNums(family, name);
    }

    @Override
    public void insertModuleRecord(String family, String name) {
        // TODO Auto-generated method stub
        //定时函数用到的
        //1 弹出除最后三条外的记录
        List<ModuleRecord_cache> mCaches=redisServiceImp.getModuleRecordOld(family, name);
        redisServiceImp.deleteModuleRecordOld(family, name);
        //2.插入到record表中
        for(ModuleRecord_cache moduleRecord_cache:mCaches){
            if(moduleRecord_cache.getEndTime() == -1 ||  moduleRecord_cache.getEndTime() == -2){
                continue;//残缺记录不计入
            }
            Module_use_record module_use_record=new Module_use_record();
            module_use_record.setEndTime(new Timestamp(moduleRecord_cache.getEndTime()));
            module_use_record.setStartTime(new Timestamp(moduleRecord_cache.getStartTime()));
            module_use_record.setFamily(family);
            module_use_record.setName(name);
            try {
                moduleDao.insertModule_use_Record(module_use_record);//插入记录
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                LOGGER.error(e.getMessage());
            }
            
            
        }
        //3.计算下时间，加入到总时间表中(如果有就update,没有就Insert)
        long timeCount=ContantUtil.conuntTotalTime(mCaches);
        if(this.getModuleTotalTimeRecord(family, name)<1){
            //插入
            Module_total_time module_total_time=new Module_total_time();
            module_total_time.setFamily(family);
            module_total_time.setName(name);
            module_total_time.setTotal_time_second(timeCount);
            moduleDao.insertModule_Total_Record(module_total_time);
            
        }else if(timeCount>0){
            //有了，更新就行
            moduleDao.updateTotalTime(family, name, timeCount);
        }
        
        
        
    }

}
