package com.fzcode.serviceauth.service;

import com.fzcode.internalcommon.constant.RedisDBEnum;
import com.fzcode.internalcommon.constant.RegisterTypeEnum;
import com.fzcode.internalcommon.dto.common.ListResponseDTO;
import com.fzcode.internalcommon.dto.serviceauth.request.AccountListRequest;
import com.fzcode.internalcommon.dto.serviceauth.response.LoginResponse;
import com.fzcode.internalcommon.dto.serviceauth.response.RegisterResponse;
import com.fzcode.internalcommon.dto.serviceauth.common.GithubUserInfo;
import com.fzcode.serviceauth.entity.Accounts;
import com.fzcode.serviceauth.entity.Authorities;
import com.fzcode.serviceauth.entity.Users;
import com.fzcode.serviceauth.exception.CustomizeException;
import com.fzcode.serviceauth.http.Mail;
import com.fzcode.serviceauth.dao.AccountDao;
import com.fzcode.serviceauth.dao.AuthorityDao;
import com.fzcode.serviceauth.dao.UserDao;
import com.fzcode.serviceauth.redis.RedisTemplateMap;
import com.fzcode.serviceauth.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class AccountService {
    private AccountDao accountDao;

    @Autowired
    public void setAccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    private UserDao userDao;

    @Autowired
    public void setUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    private AuthorityDao authorityDao;

    @Autowired
    public void setAuthorityService(AuthorityDao authorityDao) {
        this.authorityDao = authorityDao;
    }

    private Mail mail;
    @Autowired
    public void setMail(Mail mail){
        this.mail = mail;
    }

    RedisTemplateMap redisTemplateMap;
    @Autowired
    public void setRedisTemplateMap(RedisTemplateMap redisTemplateMap){
        this.redisTemplateMap = redisTemplateMap;
    }

    public LoginResponse login(String email, String password) throws CustomizeException {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Accounts userAccount = accountDao.findByAccount(email);
        if (userAccount==null||!bCryptPasswordEncoder.matches(password, userAccount.getPassword())) {
            throw new CustomizeException("用户名密码错误");
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(TokenUtils.createBearer(accountDao.findByAccount(email).getAid(), email));
        loginResponse.setRole(authorityDao.findByAccount(email).getAuthority());
        return  loginResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public RegisterResponse register(String email, String password, String code, RegisterTypeEnum registerType) throws CustomizeException {

        Accounts accounts = new Accounts();
        accounts.setAccount(email);
        accounts.setPassword(new BCryptPasswordEncoder().encode(password));
        accounts.setRegisterType(registerType.getCode());
        boolean b = accountDao.isHas(email);
        if (b) {
            throw new CustomizeException("邮箱已存在");
        }
//        String redisCode = mail.getRegisterCode(email);
        String redisCode =redisTemplateMap.get(email, RedisDBEnum.Mail).toString();
        System.out.println("redisCode:" + redisCode);
        if (!redisCode.equals(code)) {
            throw new CustomizeException("验证码错误");
        }
        Accounts accountsResult;
        try {
            accountsResult = accountDao.create(accounts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("账号创建失败");
        }
        Integer aid = accountsResult.getAid();
        Users users = new Users();
        users.setUid(aid);
        users.setUsername(email);

        try {
            userDao.create(users);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("账号信息创建失败");
        }

        Authorities authorities = new Authorities();
        authorities.setAccount(email);
        authorities.setAuthority("USER");
        try {
            authorityDao.create(authorities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("权限创建失败");
        }
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setToken(TokenUtils.createBearer(aid, email));
        registerResponse.setRole("USER");
        return registerResponse;

    }

    //    public String forget(String email, String password, String code) throws CustomizeException {
//        UserService userService = new UserService(dataSource);
//        boolean b = userService.userExists(email);
//        if (!b) {
//            throw new CustomizeException("账号不存在");
//        }
//        if (!RedisUtils.getString(email + ":" + "forget").equals(code)) {
//            throw new CustomizeException("验证码错误");
//        }
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        try {
//            userService.resetPassword(email, bCryptPasswordEncoder.encode(password));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new CustomizeException("修改失败");
//        }
//        return email;
//
//    }
//
//    public String reset(String email, String oldPassword, String newPassword) throws CustomizeException {
//        UserService userService = new UserService(dataSource);
//        boolean b = userService.userExists(email);
//        if (!b) {
//            throw new CustomizeException("账号不存在");
//        }
//        UserDetails userDetails = userService.loadUserByUsername(email);
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        if (!bCryptPasswordEncoder.matches(oldPassword, userDetails.getPassword())) {
//            throw new UsernameNotFoundException("用户名密码错误");
//        }
//        try {
//            userService.changePassword(email, newPassword, newPassword);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new CustomizeException("修改失败");
//        }
//        return email;
//
//    }
    @Transactional(rollbackFor = Exception.class)
    public RegisterResponse githubRegister(GithubUserInfo githubUserInfo) throws CustomizeException {
        String email = githubUserInfo.getEmail();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(githubUserInfo.getNode_id());
        System.out.println("password:" + password);
        Accounts account = accountDao.findByAccount(email);
        if (account != null) {
            if (!bCryptPasswordEncoder.matches(githubUserInfo.getNode_id(), account.getPassword())) {
                throw new CustomizeException("账号或密码错误，请找回密码");
            } else {
                RegisterResponse registerResponse = new RegisterResponse();
                registerResponse.setToken(TokenUtils.createBearer(account.getAid(), email));
                registerResponse.setRole(authorityDao.findByAccount(email).getAuthority());
                return registerResponse;
            }
        } else {
            Accounts saveAccount = new Accounts();
            saveAccount.setAccount(email);
            saveAccount.setRegisterType(1);
            saveAccount.setPassword(password);
            Accounts accountResult = null;
//            UserDetails newUserDetails = MyUser
//                    .withMyUsername(email)
//                    .password(new BCryptPasswordEncoder().encode(password))
//                    .accountExpired(true)
//                    .accountLocked(true)
//                    .registerType(registerType)
//                    .roles("USER")
//                    .build();
            try {
                accountResult = accountDao.create(saveAccount);
            } catch (Exception e) {
                throw new CustomizeException("账号创建失败，请重试");

            }
            Users users = new Users();
            users.setUid(accountResult.getAid());
            if (githubUserInfo.getName() != null) {
                users.setUsername(githubUserInfo.getName());
            } else {
                users.setUsername(email);
            }

            users.setAvatar(githubUserInfo.getAvatar_url());
            users.setBlog(githubUserInfo.getBlog());
            users.setGithubUrl("https://github.com/"+githubUserInfo.getLogin());
            users.setIdCard("");
            try {
                userDao.create(users);
            } catch (Exception e) {
                throw new CustomizeException("账号资料创建失败，请重试");
            }
            Authorities authorities = new Authorities();
            authorities.setAccount(email);
            authorities.setAuthority("USER");
            try {
                authorityDao.create(authorities);
            } catch (Exception e) {
                throw new CustomizeException("权限创建失败，请重试");
            }
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setToken(TokenUtils.createBearer(accountResult.getAid(), email));
            registerResponse.setRole("USER");
            return registerResponse;
        }
//        return JwtUtils.createToken(email);

//        return new SuccessResDTO("创建成功", new TokenDTO(JwtUtils.createToken(email)));

    }

    public ListResponseDTO<Map<String, Object>> findAllAccount(AccountListRequest accountListRequest) {
        return accountDao.findList(accountListRequest);
    }

    public Map<String, Object> findByAid(Integer aid) throws CustomizeException {
        return accountDao.findUserInfoByUid(aid);
    }
}
