package com.fzcode.service.auth.service;

import com.fzcode.service.auth.dto.request.AuthorityDTO;
import com.fzcode.service.auth.entity.Authorities;
import com.fzcode.service.auth.http.Gate;
import com.fzcode.service.auth.dao.AuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AuthorityService {
    private AuthorityDao authorityDao;

    @Autowired
    public void setAuthorityService(AuthorityDao authorityDao) {
        this.authorityDao = authorityDao;
    }


    public Authorities updateByAccount(AuthorityDTO authorityDTO) {
        Gate.updateAuthority(authorityDTO.getAccount(), authorityDTO.getAuthority());
        return authorityDao.update(authorityDTO.getAccount(), authorityDTO.getAuthority());
    }
}
