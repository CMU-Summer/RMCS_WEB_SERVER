package org.cmu.rmcs.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cmu.rmcs.dao.ModuleDao;
import org.cmu.rmcs.dao.RedisDao;
import org.cmu.rmcs.pojo.GroupStruct;
import org.cmu.rmcs.pojo.ModuleInfo;
import org.cmu.rmcs.pojo.ModuleRecord;
import org.cmu.rmcs.pojo.Module_total_time;
import org.cmu.rmcs.service.ModuleService;
import org.cmu.rmcs.util.ContantUtil;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
@Service
public class ModuleServiceImp implements ModuleService{
    @Resource
    private RedisDao redisDao;
    @Resource
    private ModuleDao moduleDao;
    
    
    @Override
    public String getOneModuleTotalTimeString(String family, String name) {
        // TODO Auto-generated method stub
        //获得一个格式化的字符串 xxday xxhr xxmin xxsec
        String timeString=new String(ContantUtil.TIME_STR);
       Module_total_time m_t_t= moduleDao.getModule_total_time(family, name);
       if(m_t_t == null)return String.format(timeString, 0,0,0,0); //不存在这个记录
       else {
           String timeString2=ContantUtil.formatTime(m_t_t.getTotal_time_second());
           return timeString2;
       }
    }

    @Override
    public long getOneModuleTotalTimeInt(String family, String name) {
        // TODO Auto-generated method stub
        Module_total_time m_t_t= moduleDao.getModule_total_time(family, name);
        if(m_t_t == null)return 0; //不存在这个记录
        else return m_t_t.getTotal_time_second();
    }

    @Override
    public List<ModuleRecord> getModuleRecordsLatest(String family, String name) {
        // TODO Auto-generated method stub
        //从缓存里面拿数据
        String key=family+ContantUtil.MODULE_INFIX+name;
        long size= redisDao.getListSize(key);
        List<ModuleRecord> moduleRecords=new ArrayList<>();
        List<String> moduleRecordList=new ArrayList<>();
        if(size <3){
           moduleRecordList= redisDao.getList(key, 0, -1);//全部取
        }else { //大于等于3
           moduleRecordList = redisDao.getList(key, size-3, -1);//最后三条
        }
        for(String s:moduleRecordList){
            JSONObject jsonObject=JSONObject.parseObject(s);
            ModuleRecord mRecord=JSONObject.toJavaObject(jsonObject, ModuleRecord.class);
            moduleRecords.add(mRecord);
            //添加进来
        }
        return moduleRecords;
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
        //2.插入到record表中
        //3.计算下时间，加入到总时间表中
    }

}
