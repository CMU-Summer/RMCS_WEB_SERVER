package org.cmu.rmcs.service.imp;

import java.io.Serializable;

import javax.annotation.Resource;

import org.cmu.rmcs.dao.UserDao;
import org.cmu.rmcs.pojo.User;
import org.cmu.rmcs.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImp implements UserService{
    @Resource
    private UserDao userDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);
    public boolean isUser(String userName, String password) {
        // TODO Auto-generated method stub
        try {
            return userDao.selectUser(userName, password)>=1 ? true :false;
            
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public User getUser(String userName, String password) {
        // TODO Auto-generated method stub
        return userDao.getUser(userName, password);
    }
}
