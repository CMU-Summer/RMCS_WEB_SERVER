package org.cmu.rmcs.service;

import java.util.List;

import org.cmu.rmcs.pojo.ModuleRecord;
import org.springframework.stereotype.Service;
@Service
public interface ModuleService {
    //1.获得某个module的总时长的字符串形式 (数据库+redis)
    public String getOneModuleTotalTimeString(String family ,String name);
    //2 获取某个module的总时长的长整数形式(数据库+redis,数据库的时间是保存在数据库的总时间，还要加上在redis里面的时间,redis里面的记录可能大于3条，可能小于)
    public long getOneModuleTotalTimeInt(String family ,String name);
    //3.获得某个module的Latest list记录,从redis里面取(只取最后三条,非删除的方式取)
    public List<ModuleRecord> getModuleRecordsLatest(String family ,String name);
    //4.获得redis里面的记录时间的条数，如果没有<1,需要创建(创建调dao层的就行)
    public int getModuleTotalTimeRecord(String family ,String name);
    //6.将缓存中的module_record插入数据库_永久化保存(从redis里面读，向数据库里面写(只留最后三条)，计算时间[end-start]，和时间相加(如果没有)，写入到module相应的总时间里面去)
    //**只会被定时任务调用
    public void insertModuleRecord(String family ,String name);
    
    
    
}
