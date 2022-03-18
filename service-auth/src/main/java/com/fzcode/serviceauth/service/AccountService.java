package com.fzcode.serviceauth.service;

import com.fzcode.internalcommon.constant.RegisterTypeEnum;
import com.fzcode.internalcommon.dto.common.ListDTO;
import com.fzcode.internalcommon.dto.serviceauth.request.AccountListRequest;
import com.fzcode.internalcommon.dto.serviceauth.response.LoginResponse;
import com.fzcode.internalcommon.dto.serviceauth.response.RegisterResponse;
import com.fzcode.internalcommon.dto.serviceauth.common.GithubUserInfo;
import com.fzcode.internalcommon.dto.serviceauth.response.SelfResponse;
import com.fzcode.internalcommon.exception.CustomizeException;
import com.fzcode.internalcommon.utils.JSONUtils;
import com.fzcode.serviceauth.entity.Accounts;
import com.fzcode.serviceauth.entity.Authorities;
import com.fzcode.serviceauth.entity.Users;
import com.fzcode.serviceauth.dao.AccountDao;
import com.fzcode.serviceauth.dao.AuthorityDao;
import com.fzcode.serviceauth.dao.UserDao;
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

    RedisUtils redisUtils;

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils){
        this.redisUtils = redisUtils;
    }

    PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    TokenUtils tokenUtils;
    @Autowired
    public void setTokenUtils( TokenUtils tokenUtils){
        this.tokenUtils = tokenUtils;
    }

    public LoginResponse login(String email, String password) throws CustomizeException {

        Accounts userAccount = accountDao.findByAccount(email);
        if (userAccount==null||!passwordEncoder.matches(password, userAccount.getPassword())) {
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"用户名密码错误");
        }
        LoginResponse loginResponse = new LoginResponse();
        Accounts account = accountDao.findByAccount(email);
        loginResponse.setToken(this.tokenUtils.createBearer(account.getAid(),account.getUid(), email));
        loginResponse.setRole(authorityDao.findByAccount(email).getAuthority());
        return  loginResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public RegisterResponse register(String email, String password, String code, int registerType) throws CustomizeException {
        boolean b = accountDao.isHas(email);
        if (b) {
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"邮箱已存在");
        }
        String redisCode = redisUtils.getString(email + ":register");
        if (!redisCode.equals(code)) {
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"验证码错误");
        }
        Accounts accounts = new Accounts();
        accounts.setAccount(email);
        accounts.setPassword(passwordEncoder.encode(password));
        accounts.setRegisterType(registerType);

        Accounts accountsResult;
        try {
            accountsResult = accountDao.create(accounts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"账号创建失败");
        }
        String aid = accountsResult.getAid();
        Users users = new Users();
        users.setAid(aid);
        users.setUsername(email);
        Users userResult ;
        try {
            userResult =  userDao.create(users);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"账号信息创建失败");
        }

        Authorities authorities = new Authorities();
        authorities.setAccount(email);
        authorities.setAuthority("USER");
        authorities.setAid(accountsResult.getAid());
        authorities.setUid(userResult.getUid());
        try {
            authorityDao.create(authorities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"权限创建失败");
        }
        Accounts nextAccount = new Accounts();
        nextAccount.setUid(userResult.getUid());
        accountDao.patch(accountsResult.getAid(),nextAccount);
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setToken(this.tokenUtils.createBearer(aid, userResult.getUid(),email));
        registerResponse.setRole("USER");
        return registerResponse;

    }

    //    public String forget(String email, String password, String code) throws CustomizeException {
//        UserService userService = new UserService(dataSource);
//        boolean b = userService.userExists(email);
//        if (!b) {
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"账号不存在");
//        }
//        if (!RedisUtils.getString(email + ":" + "forget").equals(code)) {
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"验证码错误");
//        }
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        try {
//            userService.resetPassword(email, bCryptPasswordEncoder.encode(password));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"修改失败");
//        }
//        return email;
//
//    }
//
//    public String reset(String email, String oldPassword, String newPassword) throws CustomizeException {
//        UserService userService = new UserService(dataSource);
//        boolean b = userService.userExists(email);
//        if (!b) {
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"账号不存在");
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
//            throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"修改失败");
//        }
//        return email;
//
//    }
    @Transactional(rollbackFor = Exception.class)
    public RegisterResponse githubRegister(GithubUserInfo githubUserInfo) throws CustomizeException {

        String email = githubUserInfo.getEmail();
        System.out.println("githubRegister："+email);
        Accounts account = accountDao.findByAccount(email);

        if (account != null) {
            if (!passwordEncoder.matches(githubUserInfo.getId().toString(), account.getPassword())) {
                throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"你这号id怎么变了");
            } else {

                RegisterResponse registerResponse = new RegisterResponse();
                registerResponse.setToken(this.tokenUtils.createBearer(account.getAid(),account.getUid(), email));
                registerResponse.setRole(authorityDao.findByAccount(email).getAuthority());
                System.out.println(JSONUtils.stringify(registerResponse));
                return registerResponse;
            }
        } else {
            String password = passwordEncoder.encode(githubUserInfo.getId().toString());
            Accounts saveAccount = new Accounts();
            saveAccount.setAccount(email);
            saveAccount.setRegisterType(RegisterTypeEnum.GITHUB_REGISTER.getCode());
            saveAccount.setPassword(password);
            Accounts accountResult ;
            try {
                accountResult = accountDao.create(saveAccount);
            } catch (Exception e) {
                throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"账号创建失败，请重试");

            }
            Users users = new Users();
            users.setAid(accountResult.getAid());
            if (githubUserInfo.getName() != null) {
                users.setUsername(githubUserInfo.getName());
            } else {
                users.setUsername(email);
            }

            users.setAvatar(githubUserInfo.getAvatar_url());
            users.setBlog(githubUserInfo.getBlog());
            users.setGithubUrl("https://github.com/"+githubUserInfo.getLogin());
            users.setIdCard("");
            Users userResult ;
            try {
                userResult = userDao.create(users);
            } catch (Exception e) {
                throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"账号资料创建失败，请重试");
            }
            Authorities authorities = new Authorities();
            authorities.setAccount(email);
            authorities.setAuthority("USER");
            authorities.setAid(accountResult.getAid());
            authorities.setUid(userResult.getUid());
            try {
                authorityDao.create(authorities);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new CustomizeException(HttpStatus.INTERNAL_SERVER_ERROR,"权限创建失败，请重试");
            }
            Accounts nextAccount = new Accounts();
            nextAccount.setUid(userResult.getUid());
            accountDao.patch(accountResult.getAid(),nextAccount);
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setToken(this.tokenUtils.createBearer(accountResult.getAid(),userResult.getUid(), email));
            registerResponse.setRole("USER");
            return registerResponse;
        }
//        return JwtUtils.createToken(email);

//        return new SuccessResDTO("创建成功", new TokenDTO(JwtUtils.createToken(email)));

    }

    public ListDTO<Accounts> findAllAccount(AccountListRequest accountListRequest) {
        return accountDao.findList(accountListRequest);
    }

    public SelfResponse findFirstByAid(String aid) throws CustomizeException {
        Accounts accounts = accountDao.findFirstByAid(aid);
        Users  users = userDao.findFirstByAid(aid);
        SelfResponse selfResponse = new SelfResponse();
        BeanUtils.copyProperties(accounts,selfResponse);
        BeanUtils.copyProperties(users,selfResponse);
        System.out.println("=============");
        System.out.println(JSONUtils.stringify(accounts));
        System.out.println(JSONUtils.stringify(users));
        System.out.println("=============");
        System.out.println(JSONUtils.stringify(selfResponse));
        System.out.println("=============");
        return selfResponse;
    }
}
