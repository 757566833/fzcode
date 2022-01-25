package com.fzcode.serviceauth.service;

import com.fzcode.internalcommon.constant.RegisterTypeEnum;
import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.serviceauth.common.GithubUserInfo;
import com.fzcode.internalcommon.dto.serviceauth.request.AccountListRequest;
import com.fzcode.internalcommon.dto.serviceauth.response.LoginResponse;
import com.fzcode.internalcommon.dto.serviceauth.response.RegisterResponse;
import com.fzcode.internalcommon.dto.serviceauth.response.SelfResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.utils.JSONUtils;
import com.fzcode.serviceauth.dao.AccountDao;
import com.fzcode.serviceauth.dao.AuthorityDao;
import com.fzcode.serviceauth.dao.UserDao;
import com.fzcode.serviceauth.entity.Accounts;
import com.fzcode.serviceauth.entity.Authorities;
import com.fzcode.serviceauth.entity.Users;
import com.fzcode.serviceauth.util.RedisUtils;
import com.fzcode.serviceauth.util.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


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
