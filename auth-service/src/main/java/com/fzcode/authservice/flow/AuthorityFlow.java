package com.fzcode.authservice.flow;

import com.fzcode.authservice.dto.request.AuthorityDTO;
import com.fzcode.authservice.dto.request.list.AccountDTO;
import com.fzcode.authservice.dto.response.GithubUserInfo;
import com.fzcode.authservice.dto.response.LoginResDTO;
import com.fzcode.authservice.entity.Accounts;
import com.fzcode.authservice.entity.Authorities;
import com.fzcode.authservice.entity.Users;
import com.fzcode.authservice.exception.CustomizeException;
import com.fzcode.authservice.http.Gate;
import com.fzcode.authservice.http.Mail;
import com.fzcode.authservice.service.AccountService;
import com.fzcode.authservice.service.AuthorityService;
import com.fzcode.authservice.service.UserService;
import com.fzcode.authservice.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class AuthorityFlow {
    private AuthorityService authorityService;

    @Autowired
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }


    public Authorities updateByAccount(AuthorityDTO authorityDTO) {
        Gate.updateAuthority(authorityDTO.getAccount(), authorityDTO.getAuthority());
        return authorityService.update(authorityDTO.getAccount(), authorityDTO.getAuthority());
    }
}
