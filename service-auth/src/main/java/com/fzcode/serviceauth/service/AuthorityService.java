package com.fzcode.serviceauth.service;

import com.fzcode.serviceauth.dto.request.AuthorityDTO;
import com.fzcode.serviceauth.entity.Authorities;
import com.fzcode.serviceauth.http.Gate;
import com.fzcode.serviceauth.dao.AuthorityDao;
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
