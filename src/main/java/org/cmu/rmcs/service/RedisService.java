package org.cmu.rmcs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cmu.rmcs.pojo.GroupStruct;
import org.cmu.rmcs.pojo.GroupfeedbackCustomStruct;
import org.cmu.rmcs.pojo.ModuleRecord;
import org.cmu.rmcs.pojo.ModuleRecord_cache;
import org.springframework.stereotype.Service;
@Service
public interface RedisService {
    //redisService的接口，具体操作有哪些呢0
    //1.从缓存中获得Group的列表 1.从group的key里面取 2.取这些key对应的value ,取出group的信息
    public List<GroupStruct> getGroupStrInCache();
    
    //2.在缓存里面添加一个group,1.group 的set 添加，2。添加 gn_g的key  3.添加gn_g_gfd的key
    public boolean addGroupToCache(GroupStruct gs);
    
    //3.判断这个group在不在缓存里面,1.获得group
    public boolean isGroupInCache(String groupName);//
    
    //4，取某个group的feedbacklist的指定位置以后的所有条，位置在webSocket里面维护
    public List<GroupfeedbackCustomStruct> getGroupFdList(String groupName,long lastEnd);
    
    //5.删除一个group 1.删group set里面的值 2.删 redis里面的gname_g的键，3 删_gfd的键
    
    public boolean deleteGroupIncache(String groupName);
    
    //6 获得当前的f and n 的 map:1.取family set里面的值 2 以其为key，去获得 name的list
    
    public Map<String, ArrayList<String>> getFamilyAndItsNames();
    
    //7  取出group的名字的set
    
    public Set<String> getGroupNamesFromCache();
    
    //8 取出部分group
    public List<GroupStruct> getSomeGroupFromCache(Set<String> nameList);
    
    //9 获得list或set的长度
    public long getSetOrListSize(String setOrListKey,int type);
    
    //10 删除groupfeedbacklist的前n条
    public boolean clearGroupFeeback(String groupName,long untilNum);
    
    //11 删除group的feedback
    public boolean deleteGroupFeedback(String groupNmae);
    
    //12 hack 代码 获取 fixedgroup
    
    public List<GroupStruct> getfixedGroupFromCache(Set<String> s);
    
    //13 获取fixedGroup的name set
    public Set<String> getfixedGroupsNameFromCache();
    
    //14 hack 获取fixed和人为添加的
    public Set<String> getGroupAndFixNamesFromCache();
    
    //15 hack 删除fixed group
    public boolean deleteFixedGroupInCache(String groupName);
    
    //16 计算缓存内module的总时间,所有的,返回long
    public long countModuleTimeInCache(String family ,String name);
    //17 将redis内，moduleRecord最新的返回(时间是字符串类型)
    public List<ModuleRecord> getModuleRecordLatest(String family ,String name);

    //18 ，取指定范围的moduleRecord_cache
    public List<ModuleRecord_cache> getModuleRecordRange(String family ,String name,long start,long end);
    
    //19 清理module的老旧记录
    public void deleteModuleRecordOld(String family,String name);
    
    //20 17de int
    public List<ModuleRecord_cache> getModuleRecordLatest_Long(String family ,String name);
    //21 取module的老记录
    public List<ModuleRecord_cache> getModuleRecordOld(String family ,String name);
}
