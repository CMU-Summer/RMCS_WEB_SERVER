package org.cmu.rmcs.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.cmu.rmcs.dao.RedisDao;
import org.cmu.rmcs.pojo.GroupStruct;
import org.cmu.rmcs.pojo.GroupfeedbackCustomStruct;
import org.cmu.rmcs.service.RedisService;
import org.cmu.rmcs.util.ContantUtil;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
@Service
public class RedisServiceImp implements RedisService{
    @Resource
    private RedisDao redisDao;
    
    @Override
    public List<GroupStruct> getGroupStrInCache() {
        // TODO Auto-generated method stub
       List<String> groupNameList= redisDao.getSet("group");
       List<GroupStruct> groupStructs=new ArrayList<>();
        for(String gname : groupNameList){
            if(redisDao.isKeyExist(gname)){
                String jsonString=redisDao.getStr(gname);
                GroupStruct groupStruct=(GroupStruct) JSONObject.parse(jsonString);
                groupStructs.add(groupStruct);
                
            }
            
            
        }
        
        return groupStructs;
    }

    @Override
    public boolean addGroupToCache(GroupStruct gs) {
        // TODO Auto-generated method stub
        if(redisDao.isKeyExist(gs.getName()) == false){
            //不存在这个键的,保证后台所有到redisService里面都是带_g的
            //给这个键放set里面
            redisDao.addValueToset(ContantUtil.GROUP_SET_KEY, gs.getName());
           //给一个Key，存东西
            return redisDao.setKeyAndValue(gs.getName(), JSONObject.toJSONString(gs));
            
        }
        
        
        return false;
    }

    @Override
    public boolean isGroupInCache(String groupName) {
        // TODO Auto-generated method stub
        return redisDao.isInSet(ContantUtil.GROUP_SET_KEY, groupName);
    }

    @Override
    public List<GroupfeedbackCustomStruct> getGroupFdList(String groupName,
            int lastEnd) {
        // TODO Auto-generated method stub
        //从上一次的地方一直往后取
      List<String> fdJsonList= redisDao.getList(groupName+ContantUtil.POSTFIX_GROUP_FEEDBACK_KEY, lastEnd  , -1);
      List<GroupfeedbackCustomStruct> fdCustomStructs=new ArrayList<>();
      for(String s : fdJsonList){
          fdCustomStructs.add((GroupfeedbackCustomStruct) JSONObject.parse(s));
          
      }
      return fdCustomStructs;
    
    }

    @Override
    public boolean deleteGroupIncache(String groupName) {
        // TODO Auto-generated method stub
        //从group中删除
        redisDao.deletSetElement(ContantUtil.GROUP_SET_KEY, groupName);
       return redisDao.deletKey(groupName)>0?true:false;
       
    }

    @Override
    public Map<String, ArrayList<String>> getFamilyAndItsNames() {
        // TODO Auto-generated method stub
        List<String> fSet= redisDao.getSet(ContantUtil.FAMILY_SET_KEY);
        Map<String, ArrayList<String>> fandItnames=new HashMap<String, ArrayList<String>>();
        for(String fname:fSet){
            ArrayList<String> names= (ArrayList<String>) redisDao.getList(fname, 0, -1);
            if(names.size()>0){
                fandItnames.put(fname, names);//只要长度不为零就加进去
                
            }
        }
        return fandItnames;
    }

    @Override
    public Set<String> getGroupNamesFromCache() {
        // TODO Auto-generated method stub
        
        
        return new HashSet<String>(redisDao.getSet(ContantUtil.GROUP_SET_KEY));
    }

    @Override
    public List<GroupStruct> getSomeGroupFromCache(Set<String> nameList) {
        // TODO Auto-generated method stub
        List<GroupStruct> list=new ArrayList<>();
        for(String s:nameList){
            String groupJsonString=redisDao.getStr(s);
            if(StringUtil.isBlank(groupJsonString) == false){
                list.add((GroupStruct) JSONObject.parse(groupJsonString));
            }
            
            
        }
        return list;
        

    }

}
