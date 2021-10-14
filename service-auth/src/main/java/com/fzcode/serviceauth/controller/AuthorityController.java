package com.fzcode.serviceauth.controller;

import com.fzcode.internalcommon.dto.serviceauth.request.GetAuthorityRequest;
import com.fzcode.serviceauth.entity.Authorities;
import com.fzcode.serviceauth.dao.AuthorityDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "权限模块")
@RestController
@RequestMapping(value = "/authority",consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthorityController {
    private AuthorityDao authorityDao;

    @Autowired
    public void setAuthorityService(AuthorityDao authorityDao) {
        this.authorityDao = authorityDao;
    }

    @ApiOperation(value = "获取权限")
    @PostMapping(value = "/get")
    public Authorities getAuth(@RequestBody GetAuthorityRequest getAuthorityRequest) {
        Authorities authorities = authorityDao.findByAccount(getAuthorityRequest.getAccount());
        return authorities;
    }
}
