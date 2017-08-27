package org.cmu.rmcs.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
@Service
public class RedisDao {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static Logger LOGGER = LoggerFactory.getLogger(RedisDao.class);

    // 字符串获取 

    public String getStr(final String keyName) {
        try {
            BoundValueOperations<String, String> vOpt= redisTemplate.boundValueOps(keyName);
            return  vOpt.get();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LOGGER.error(e.getMessage());
             return new String("");
        }
       

    }

    // 设置key,并给一个string
    public boolean setKeyAndValue(final String keyName, final String value) {
        try {
            
            BoundValueOperations<String, String> vOpt= redisTemplate.boundValueOps(keyName);
           
            vOpt.set(value);
            return true;
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return false;
        }
        
     

    }

    // 获取数组,起始到结束
    public List<String> getList(final String listKey, final long start,
            final int end) {
        try {
            BoundListOperations<String, String> lOpt=redisTemplate.boundListOps(listKey);
            return lOpt.range(start, end);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
        
       

    }

    // 获取集合/包装成数组
    public List<String> getSet( String setKey) {
        
     
        try {
            SetOperations<String, String> sOpt= redisTemplate.opsForSet();
            boolean a=redisTemplate.hasKey(setKey);
            Set<String> sets= sOpt.members(setKey);
            ArrayList<String> resArrayList = new ArrayList<String>();
            resArrayList.addAll(sets);
            return resArrayList;
           
        } catch (Exception e) {
            // TODO: handle exception 
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return new ArrayList<String>();
        }
        
       
    }

    // 删除某个集合元素
    public boolean deletSetElement(final String setKey,final String value ) {
        try {
            BoundSetOperations<String, String> bopts= redisTemplate.boundSetOps(setKey);
            boolean nums=bopts.remove(value);
        
            return nums;
           
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return false;
        }
 
    }

    // 删除列表元素,
    public boolean deletListElementUntil(final String listkey,final int end) {
        try {
            BoundListOperations<String, String> bopts= redisTemplate.boundListOps(listkey);
            bopts.trim(end , -1);
                
        
            return true;
           
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return false;
        }
       
    }
    // 获得列表长度
    public long getListSize(final String listKey) {
        try {
            BoundListOperations<String, String> bopts= redisTemplate.boundListOps(listKey);
            return bopts.size();
 
           
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return -1;
        }
        
        
        
        
        
    }
    //获得集合长度
    public long getSetSize(final String setKey){
        try {
            BoundSetOperations<String, String> bopts= redisTemplate.boundSetOps(setKey);
            return bopts.size();
 
           
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return -1;
        }
 
    }
    
    
    
    
    // 删除key
    public long deletKey(final String key) {
        try {
            
           redisTemplate.delete(key);;
            return 1;
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return -1;
        }
        
        
        
     
    }
    
    //判断key是否存在
    public boolean isKeyExist(final String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return false;
        }

    }
    
    
    //判断集合中是否有这个成员
    public boolean isInSet(final String key,final String value){
        try {
            BoundSetOperations<String, String> sOpt = redisTemplate.boundSetOps(key);
            return sOpt.isMember(value);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return false;
        }  
//        return redisTemplate.execute(new RedisCallback<Boolean>() {
//
//            public Boolean doInRedis(RedisConnection redisConnection)
//                    throws DataAccessException {
//                // TODO Auto-generated method stub
//                try {
//                    // 用来吧string 搞成字符数组的
//                    RedisSerializer<String> serializer = redisTemplate
//                            .getStringSerializer();
//                    byte[] keyBty=serializer.serialize(key);
//                    byte[] valueBty=serializer.serialize(value);
//                    return redisConnection.sIsMember(keyBty, valueBty);
//                } catch (DataAccessException e) {
//                    // TODO: handle exception
//                    e.printStackTrace();
//                    LOGGER.error(e.getMessage());
//                    return false;
//                }
//
//            }
//        });
        
        
    }

    //给集合中添加成员
    public boolean addValueToset(final String key,final String value){
        try {
            BoundSetOperations<String, String> sOpt = redisTemplate.boundSetOps(key);
            return sOpt.add(value);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return false;
        }
        
    }
    

}
