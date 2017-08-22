package org.cmu.rmcs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cmu.rmcs.pojo.GroupStruct;
import org.cmu.rmcs.pojo.GroupfeedbackCustomStruct;
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
    public List<GroupfeedbackCustomStruct> getGroupFdList(String groupName,int lastEnd);
    
    //5.删除一个group 1.删group set里面的值 2.删 redis里面的gname_g的键，3 删_gfd的键
    
    public boolean deleteGroupIncache(String groupName);
    
    //6 获得当前的f and n 的 map:1.取family set里面的值 2 以其为key，去获得 name的list
    
    public Map<String, ArrayList<String>> getFamilyAndItsNames();
    
}
