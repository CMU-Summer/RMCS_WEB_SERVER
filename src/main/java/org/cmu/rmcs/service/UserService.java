package org.cmu.rmcs.service;

import org.cmu.rmcs.pojo.User;
import org.springframework.stereotype.Service;
@Service
public interface UserService {

    public boolean isUser(String userName , String password);
    public User getUser(String userName , String password);
}
