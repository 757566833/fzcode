package com.fzcode.serviceauth.service;

import com.fzcode.internalcommon.dto.serviceauth.request.UpdateAuthorityRequest;
import com.fzcode.serviceauth.entity.Authorities;
import com.fzcode.serviceauth.dao.AuthorityDao;
import com.fzcode.serviceauth.util.AuthRedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AuthorityService {
    private AuthorityDao authorityDao;

    @Autowired
    public void setAuthorityService(AuthorityDao authorityDao) {
        this.authorityDao = authorityDao;
    }


    public Authorities updateByAccount(UpdateAuthorityRequest updateAuthorityRequest) {
        AuthRedisUtils.setHash("authority", updateAuthorityRequest.getAccount(), updateAuthorityRequest.getAuthority());
        return authorityDao.update(updateAuthorityRequest.getAccount(), updateAuthorityRequest.getAuthority());
    }
}
