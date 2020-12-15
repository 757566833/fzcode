package com.fzcode.authservice.flow;

import com.fzcode.authservice.dto.common.ListResDTO;
import com.fzcode.authservice.dto.request.list.AccountDTO;
import com.fzcode.authservice.dto.response.GithubUserInfo;
import com.fzcode.authservice.dto.response.LoginResDTO;
import com.fzcode.authservice.entity.Accounts;
import com.fzcode.authservice.entity.Authorities;
import com.fzcode.authservice.entity.Users;
import com.fzcode.authservice.exception.CustomizeException;
import com.fzcode.authservice.http.Mail;
import com.fzcode.authservice.service.AccountService;
import com.fzcode.authservice.service.AuthorityService;
import com.fzcode.authservice.service.UserService;
import com.fzcode.authservice.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Component
public class AccountFlow {
    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private AuthorityService authorityService;

    @Autowired
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    public LoginResDTO login(String email, String password) throws CustomizeException {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(password, accountService.findByAccount(email).getPassword())) {
            throw new CustomizeException("用户名密码错误");
        }
        return new LoginResDTO(TokenUtils.createBearer(accountService.findByAccount(email).getAid(), email), authorityService.findByAccount(email).getAuthority());
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginResDTO register(String email, String password, String code, Integer registerType) throws CustomizeException {

        Accounts accounts = new Accounts();
        accounts.setAccount(email);
        accounts.setPassword(new BCryptPasswordEncoder().encode(password));
        accounts.setRegisterType(registerType);
        boolean b = accountService.isHas(email);
        if (b) {
            throw new CustomizeException("邮箱已存在");
        }
        String redisCode = Mail.getRegisterCode(email);
        System.out.println("redisCode:" + redisCode);
        if (!redisCode.equals(code)) {
            throw new CustomizeException("验证码错误");
        }
        Accounts accountsResult;
        try {
            accountsResult = accountService.create(accounts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("账号创建失败");
        }
        Integer aid = accountsResult.getAid();
        Users users = new Users();
        users.setUid(aid);
        users.setUsername(email);

        try {
            userService.create(users);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("账号信息创建失败");
        }

        Authorities authorities = new Authorities();
        authorities.setAccount(email);
        authorities.setAuthority("USER");
        try {
            authorityService.create(authorities);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomizeException("权限创建失败");
        }
        return new LoginResDTO(TokenUtils.createBearer(aid, email), "USER");

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
    public LoginResDTO githubRegister(GithubUserInfo githubUserInfo) throws CustomizeException {
        System.out.println("node_id:" + githubUserInfo.getNode_id());
        String email = githubUserInfo.getEmail();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(githubUserInfo.getNode_id());
        System.out.println("password:" + password);
        Accounts account = accountService.findByAccount(email);
        if (account != null) {
            if (!bCryptPasswordEncoder.matches(githubUserInfo.getNode_id(), account.getPassword())) {
                return new LoginResDTO("账号或密码错误，请找回密码", "USER");
//                return "账号或密码错误，请找回密码";
            } else {
                return new LoginResDTO(TokenUtils.createBearer(account.getAid(), email), authorityService.findByAccount(email).getAuthority());
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
//                System.out.println(JSON.toJSONString(saveAccount));
                accountResult = accountService.create(saveAccount);
            } catch (Exception e) {
                return new LoginResDTO("账号创建失败", "USER");
//                return "账号创建失败";
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
            users.setGithubUrl(githubUserInfo.getGists_url());
            users.setIdCard("");
            users.setGithubUrl(githubUserInfo.getGists_url());
//            users.set
            try {
                userService.create(users);
            } catch (Exception e) {
                return new LoginResDTO("账号资料创建失败", "USER");
//                return "账号创建失败";
            }
            Authorities authorities = new Authorities();
            authorities.setAccount(email);
            authorities.setAuthority("USER");
            try {
                authorityService.create(authorities);
            } catch (Exception e) {
                return new LoginResDTO("权限创建失败", "USER");
            }
            return new LoginResDTO(TokenUtils.createBearer(accountResult.getAid(), email), "USER");
        }
//        return JwtUtils.createToken(email);

//        return new SuccessResDTO("创建成功", new TokenDTO(JwtUtils.createToken(email)));

    }

    public ListResDTO<List<Map<String, Object>>> findAllAccount(AccountDTO accountDTO) {
//        Integer
        return accountService.findList(accountDTO);
    }

    public Map<String, Object> findByAid(Integer aid) throws CustomizeException {
//        Integer
        return accountService.findUserInfoByUid(aid);
    }
}
