package com.fzcode.api.blog.service.impl;

import com.fzcode.api.blog.request.AccountRequest;
import com.fzcode.api.blog.response.LoginResponse;
import com.fzcode.api.blog.service.AuthService;
import com.fzcode.internal.common.dto.common.GithubUserInfoDTO;
import com.fzcode.internal.common.dto.common.ListResponseDTO;

import java.util.List;
import java.util.Map;

public class AuthServiceImpl implements AuthService {
    @Override
    public LoginResponse login(String email, String password) {
        return null;
    }

    @Override
    public LoginResponse register(String email, String password, String code, Integer registerType) {
        return null;
    }

    @Override
    public LoginResponse githubRegister(GithubUserInfoDTO githubUserInfo) {
        return null;
    }

    @Override
    public ListResponseDTO<List<Map<String, Object>>> findAllAccount(AccountRequest accountRequest) {
        return null;
    }

    @Override
    public Map<String, Object> findByAid(Integer aid) {
        return null;
    }
}
