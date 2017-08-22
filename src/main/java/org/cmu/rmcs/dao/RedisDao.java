package org.cmu.rmcs.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisDao {
    @Autowired
    protected RedisTemplate<Serializable, Serializable> redisTemplate;

    private static Logger LOGGER = LoggerFactory.getLogger(RedisDao.class);

    // 字符串获取

    public String getStr(final String keyName) {
        return redisTemplate.execute(new RedisCallback<String>() {

            public String doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                // TODO Auto-generated method stub

                try {
                    // 用来吧string 搞成字符数组的

                    RedisSerializer<String> serializer = redisTemplate
                            .getStringSerializer();
                    byte[] keyByt = serializer.serialize(keyName);
                    return new String(redisConnection.get(keyByt));// 返回的序列包装成字符串

                } catch (DataAccessException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());

                }
                return new String("");
            }
        });

    }

    // 设置key,并给一个string
    public boolean setKeyAndValue(final String keyName, final String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {

            public Boolean doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                try {
                    // 用来吧string 搞成字符数组的
                    RedisSerializer<String> serializer = redisTemplate
                            .getStringSerializer();
                    byte[] keyByt = serializer.serialize(keyName);
                    byte[] valueByt = serializer.serialize(value);
                    return redisConnection.setNX(keyByt, valueByt);
                } catch (DataAccessException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                }
                return false;
            }
        });

    }

    // 获取数组,起始到结束
    public List<String> getList(final String listKey, final int start,
            final int end) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {

            public List<String> doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                try {
                    // 用来吧string 搞成字符数组的
                    RedisSerializer<String> serializer = redisTemplate
                            .getStringSerializer();
                    byte[] keyByt = serializer.serialize(listKey);
                    List<byte[]> bytesList = redisConnection.lRange(keyByt,
                            start, end);
                    ArrayList<String> resArrayList = new ArrayList<String>();
                    for (byte[] bs : bytesList) {
                        resArrayList.add(new String(bs));

                    }
                    return resArrayList;

                } catch (DataAccessException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                    return new ArrayList<String>();
                }

            }
        });

    }

    // 获取集合/包装成数组
    public List<String> getSet(final String setKey) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {

            public List<String> doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                try {
                    // 用来吧string 搞成字符数组的
                    RedisSerializer<String> serializer = redisTemplate
                            .getStringSerializer();
                    byte[] keyByt = serializer.serialize(setKey);
                    Set<byte[]> bytesList = redisConnection.sMembers(keyByt);//取集合里面的所有成员
                    ArrayList<String> resArrayList = new ArrayList<String>();
                    for (byte[] bs : bytesList) {
                        resArrayList.add(new String(bs));
                    }
                    return resArrayList;
                } catch (DataAccessException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                    return new ArrayList<String>();
                }

            }
        });
    }

    // 删除某个集合元素
    public Long deletSetElement(final String setKey,final String value ) {
        return redisTemplate.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                try {
                    // 用来吧string 搞成字符数组的
                    RedisSerializer<String> serializer = redisTemplate
                            .getStringSerializer();
                    byte[] keyByt = serializer.serialize(setKey);
                    byte[] vByt=serializer.serialize(value);
                    return redisConnection.sRem(keyByt, vByt);
                } catch (DataAccessException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                    return (long) 0;
                }

            }
        });
    }

    // 删除列表元素,
    public boolean deletListElementUntil(final String listkey,final int end) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {

            public Boolean doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                try {
                    // 用来吧string 搞成字符数组的
                    RedisSerializer<String> serializer = redisTemplate
                            .getStringSerializer();
                    byte[] keyBty=serializer.serialize(listkey);
                    redisConnection.lTrim(keyBty, end, -1);
                    return true;
                } catch (DataAccessException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                    return false;
                }

            }
        });
    }
    // 获得列表长度
    public long getListSize(final String listKey) {
        return redisTemplate.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                try {
                    // 用来吧string 搞成字符数组的
                    RedisSerializer<String> serializer = redisTemplate
                            .getStringSerializer();
                    byte[] keyBty=serializer.serialize(listKey);
                 
                    return redisConnection.lLen(keyBty);
                } catch (DataAccessException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                    return (long) -1;
                }

            }
        });
    }
    //获得集合长度
    public long getSetSize(final String setKey){
        return redisTemplate.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                try {
                    // 用来吧string 搞成字符数组的
                    RedisSerializer<String> serializer = redisTemplate
                            .getStringSerializer();
                    byte[] keyBty=serializer.serialize(setKey);
                 
                    return redisConnection.sCard(keyBty);
                } catch (DataAccessException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                    return (long) -1;
                }

            }
        });
        
        
        
    }
    
    
    
    
    // 删除key
    public long deletKey(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {

            public Long doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                try {
                    // 用来吧string 搞成字符数组的
                    RedisSerializer<String> serializer = redisTemplate
                            .getStringSerializer();
                    byte[] keyBty=serializer.serialize(key);
                 
                    return redisConnection.del(keyBty);
                } catch (DataAccessException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                    return (long) -1;
                }

            }
        });
    }
    
    //判断集合中是否有这个成员
    
}
