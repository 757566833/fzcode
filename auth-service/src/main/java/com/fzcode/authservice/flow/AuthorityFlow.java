package com.fzcode.authservice.flow;

import com.fzcode.authservice.dto.request.AuthorityDTO;
import com.fzcode.authservice.entity.Authorities;
import com.fzcode.authservice.http.Gate;
import com.fzcode.authservice.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
