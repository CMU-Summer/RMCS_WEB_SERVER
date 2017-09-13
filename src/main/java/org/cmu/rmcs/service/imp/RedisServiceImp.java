package org.cmu.rmcs.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.cmu.rmcs.dao.RedisDao;
import org.cmu.rmcs.pojo.GroupStruct;
import org.cmu.rmcs.pojo.GroupfeedbackCustomStruct;
import org.cmu.rmcs.pojo.ModuleRecord;
import org.cmu.rmcs.pojo.ModuleRecord_cache;
import org.cmu.rmcs.service.RedisService;
import org.cmu.rmcs.util.ContantUtil;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

@Service
public class RedisServiceImp implements RedisService {
    @Resource
    private RedisDao redisDao;

    @Override
    public List<GroupStruct> getGroupStrInCache() {
        // TODO Auto-generated method stub
        List<String> groupNameList = redisDao.getSet("group");
        List<GroupStruct> groupStructs = new ArrayList<>();
        for (String gname : groupNameList) {
            if (redisDao.isKeyExist(gname)) {
                String jsonString = redisDao.getStr(gname);
                JSONObject jsonObject = JSONObject.parseObject(jsonString);

                GroupStruct groupStruct = JSONObject.toJavaObject(jsonObject,
                        GroupStruct.class);
                groupStructs.add(groupStruct);

            }

        }

        return groupStructs;
    }

    @Override
    public boolean addGroupToCache(GroupStruct gs) {
        // TODO Auto-generated method stub
        if (redisDao.isKeyExist(gs.getName()) == false) {
            // 不存在这个键的,保证后台所有到redisService里面都是带_g的
            // 给这个键放set里面
            redisDao.addValueToset(ContantUtil.GROUP_SET_KEY, gs.getName());
            // 给一个Key，存东西
            return redisDao.setKeyAndValue(gs.getName(),
                    JSONObject.toJSONString(gs));

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
            long lastEnd) {
        // TODO Auto-generated method stub
        // 从上一次的地方一直往后取
        List<String> fdJsonList = redisDao.getList(groupName
                + ContantUtil.POSTFIX_GROUP_FEEDBACK_KEY, lastEnd, -1);
        List<GroupfeedbackCustomStruct> fdCustomStructs = new ArrayList<>();
        for (String s : fdJsonList) {
            JSONObject jsonObject = JSONObject.parseObject(s);
            GroupfeedbackCustomStruct groupfeedbackCustomStruct = JSONObject
                    .toJavaObject(jsonObject, GroupfeedbackCustomStruct.class);
            fdCustomStructs.add(groupfeedbackCustomStruct);

        }
        return fdCustomStructs;

    }

    @Override
    public boolean deleteGroupIncache(String groupName) {
        // TODO Auto-generated method stub
        // 从group中删除
        redisDao.deletSetElement(ContantUtil.GROUP_SET_KEY, groupName);
        return redisDao.deletKey(groupName) > 0 ? true : false;

    }

    @Override
    public Map<String, ArrayList<String>> getFamilyAndItsNames() {
        // TODO Auto-generated method stub
        List<String> fSet = redisDao.getSet(ContantUtil.FAMILY_SET_KEY);
        Map<String, ArrayList<String>> fandItnames = new HashMap<String, ArrayList<String>>();
        for (String fname : fSet) {
            ArrayList<String> names = (ArrayList<String>) redisDao
                    .getSet(fname);
            if (names.size() > 0) {
                fandItnames.put(fname, names);// 只要长度不为零就加进去

            }
        }
        return fandItnames;
    }

    @Override
    public Set<String> getGroupNamesFromCache() {
        // TODO Auto-generated method stub
        try {
            List<String> a = redisDao.getSet(ContantUtil.GROUP_SET_KEY);
            return new HashSet<String>(a);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new HashSet<String>();
        }

    }

    @Override
    public List<GroupStruct> getSomeGroupFromCache(Set<String> nameList) {
        // TODO Auto-generated method stub
        List<GroupStruct> list = new ArrayList<>();
        for (String s : nameList) {
            String groupJsonString = redisDao.getStr(s);
            if (StringUtil.isBlank(groupJsonString) == false) {
                JSONObject jsonObject = JSONObject.parseObject(groupJsonString);
                GroupStruct groupStruct = JSONObject.toJavaObject(jsonObject,
                        GroupStruct.class);
                list.add(groupStruct);
            }

        }
        return list;

    }

    @Override
    public long getSetOrListSize(String setOrListKey, int type) {
        // TODO Auto-generated method stub
        if (type == ContantUtil.LEN_TYPE_LIST) {
            return redisDao.getListSize(setOrListKey);

        } else {
            return redisDao.getSetSize(setOrListKey);

        }
    }

    @Override
    public boolean clearGroupFeeback(String groupName, long untilNum) {
        // TODO Auto-generated method stub
        return redisDao.deletListElementUntil(groupName
                + ContantUtil.POSTFIX_GROUP_FEEDBACK_KEY, untilNum);

    }

    @Override
    public boolean deleteGroupFeedback(String groupNmae) {
        // TODO Auto-generated method stub
        long keys = redisDao.deletKey(groupNmae
                + ContantUtil.POSTFIX_GROUP_FEEDBACK_KEY);

        return keys >= 1 ? true : false;
    }

    @Override
    public List<GroupStruct> getfixedGroupFromCache(Set<String> fixedSet) {
        // TODO Auto-generated method stub
        // fixedSet是要新添加的
        Set<String> fixedGroupNameSet = redisDao
                .getSpecPostfixKey(ContantUtil.POSTFIX_FIXED_GROUP_KEY);
        Set<String> fixedGroupNameSet1 = new HashSet<>(fixedGroupNameSet);
        for (String s : fixedGroupNameSet1) {
            if (fixedSet.contains(s) == false) {
                fixedGroupNameSet.remove(s);// 如果fixedSet是需要的，如果没有，就从这里面remove掉
            }
        }
        List<GroupStruct> arryList = new ArrayList<>();
        for (String s : fixedGroupNameSet) {
            String fixGroupJsonStr = redisDao.getStr(s);
            if (StringUtil.isBlank(fixGroupJsonStr) == false) {
                JSONObject jsonObject = JSONObject.parseObject(fixGroupJsonStr);
                GroupStruct groupStruct = JSONObject.toJavaObject(jsonObject,
                        GroupStruct.class);
                arryList.add(groupStruct);
            }
        }
        return arryList;
    }

    @Override
    public Set<String> getfixedGroupsNameFromCache() {
        // TODO Auto-generated method stub
        return redisDao.getSpecPostfixKey(ContantUtil.POSTFIX_FIXED_GROUP_KEY);
    }

    @Override
    public Set<String> getGroupAndFixNamesFromCache() {
        // TODO Auto-generated method stub
        Set<String> s1 = this.getGroupNamesFromCache();
        Set<String> s2 = this.getfixedGroupsNameFromCache();
        s1.addAll(s2); // 全部加在一起
        return s1;
    }

    @Override
    public boolean deleteFixedGroupInCache(String groupName) {
        // TODO Auto-generated method stub
        return redisDao.deletKey(groupName) >= 1 ? true : false;
    }

    @Override
    public long countModuleTimeInCache(String family, String name) {
        // TODO Auto-generated method stub
        // 计算cache里面Module的总使用时间，
        List<ModuleRecord_cache> mlist = this.getModuleRecordRange(family,
                name, 0, -1);

        return ContantUtil.conuntTotalTime(mlist);
    }

    @Override
    public List<ModuleRecord> getModuleRecordLatest(String family, String name) {
        // TODO Auto-generated method stub
        List<ModuleRecord> moduleRecords = new ArrayList<>();
        List<ModuleRecord_cache> moduleRecordList = this.getModuleRecordLatest_Long(family, name);
        
        for (ModuleRecord_cache mRecord : moduleRecordList) {
            
            ModuleRecord moduleRecord = new ModuleRecord();
            SimpleDateFormat timeformat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            if (mRecord.getEndTime() == -1) {
                moduleRecord.setEndTime(" ");
            } else if (mRecord.getEndTime() == -2) {
                moduleRecord.setEndTime(" UNKNOWN");
            } else {
                moduleRecord.setEndTime(timeformat.format(new Date(mRecord
                        .getEndTime())));
            }
            moduleRecord.setStartTime(timeformat.format(new Date(mRecord
                    .getStartTime())));
            moduleRecords.add(moduleRecord);
        }
        return moduleRecords;
    }

    @Override
    public List<ModuleRecord_cache> getModuleRecordRange(String family,
            String name, long start, long end) {
        // TODO Auto-generated method stub
        String key = family + ContantUtil.MODULE_INFIX + name;
        List<String> strings = redisDao.getList(key, start, end);
        List<ModuleRecord_cache> moduleRecords = new ArrayList<>();
        for (String s : strings) {
            JSONObject jsonObject = JSONObject.parseObject(s);
            ModuleRecord_cache mRecord = JSONObject.toJavaObject(jsonObject,
                    ModuleRecord_cache.class);
            moduleRecords.add(mRecord);
        }
        return moduleRecords;
    }

    @Override
    public void deleteModuleRecordOld(String family, String name) {
        // TODO Auto-generated method stub
        String key = family + ContantUtil.MODULE_INFIX + name;
        long size = redisDao.getListSize(key);
        if (size > 3) {
            // 只有超过3条记录才清理,只保留-3 -2 -1三条
            redisDao.deletListElementUntil(key, -3);

        }
    }

    @Override
    public List<ModuleRecord_cache> getModuleRecordLatest_Long(String family,
            String name) {
        // TODO Auto-generated method stub
        String key = family + ContantUtil.MODULE_INFIX + name;
        long size = redisDao.getListSize(key);
        List<ModuleRecord_cache> moduleRecords = new ArrayList<>();
        List<String> moduleRecordList = new ArrayList<>();
        if (size < 3) {
            moduleRecordList = redisDao.getList(key, 0, -1);// 全部取
        } else { // 大于等于3
            moduleRecordList = redisDao.getList(key, -3, -1);// 最后三条
        }
        for (String s : moduleRecordList) {
            JSONObject jsonObject = JSONObject.parseObject(s);
            ModuleRecord_cache mRecord = JSONObject.toJavaObject(jsonObject,
                    ModuleRecord_cache.class);
            moduleRecords.add(mRecord);
        }
        return moduleRecords;
    }

    @Override
    public List<ModuleRecord_cache> getModuleRecordOld(String family,
            String name) {
        // TODO Auto-generated method stub
        String key = family + ContantUtil.MODULE_INFIX + name;
        long size = redisDao.getListSize(key);
        List<ModuleRecord_cache> moduleRecords = new ArrayList<>();
        List<String> moduleRecordList = new ArrayList<>();
        if (size > 3) {
            moduleRecordList = redisDao.getList(key, 0, -4);// 除最后三条全部取 ... -4 -3 -2 -1
            for (String s : moduleRecordList) {
                JSONObject jsonObject = JSONObject.parseObject(s);
                ModuleRecord_cache mRecord = JSONObject.toJavaObject(jsonObject,
                        ModuleRecord_cache.class);
                moduleRecords.add(mRecord);
            }
        }
        return moduleRecords;
    }

}
