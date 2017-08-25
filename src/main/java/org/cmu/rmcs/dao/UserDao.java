package org.cmu.rmcs.dao;

import org.cmu.rmcs.pojo.User;
import org.springframework.stereotype.Service;
@Service
public interface UserDao {
    //查找用户
    public int selectUser(String username,String pwd);
    //从表中获得用户信息;
    public User getUser(String username,String pwd); 
}
