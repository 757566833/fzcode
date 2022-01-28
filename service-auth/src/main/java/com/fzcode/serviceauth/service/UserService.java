package com.fzcode.serviceauth.service;

import com.fzcode.serviceauth.dao.UserDao;
import com.fzcode.serviceauth.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private UserDao userDao;
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public Users findById(String uid) {
        return userDao.findById(uid);
    }


}
