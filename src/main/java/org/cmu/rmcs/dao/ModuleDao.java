package org.cmu.rmcs.dao;

import java.sql.Timestamp;
import java.util.List;

import org.cmu.rmcs.pojo.Module_total_time;
import org.cmu.rmcs.pojo.Module_use_record;

public interface ModuleDao {

    //1.插入一条在module_total_time的记录
    public int insertModule_Total_Record(Module_total_time module_total_time);
    //2.插入一条在module_use_record的记录
    public int insertModule_use_Record(Module_use_record module_use_record);
    //3.获取module_total_time中的指定条件的条数
    public int getModule_total_time_RecordNums(String family , String name);
    //4 从module_total_time中取指定条件的记录
    public Module_total_time getModule_total_time(String family , String name);
    //5 从module_use_record中取所有的记录
    public Module_use_record[] getModule_use_records(String family , String name);
    //6 获得指定时间段的内的module_use_record list
    public Module_use_record[] getModule_use_recordsByTime(String family , String name,Timestamp start,Timestamp end);
    //7 获得某个指定时间的module_use_record
    public Module_use_record getOneModule_use_recordByTime(String family , String name,Timestamp start,Timestamp end);
    //8 更新module_total_time某个记录的时间
    public int updateTotalTime(String family , String name,long increateTime);
}
