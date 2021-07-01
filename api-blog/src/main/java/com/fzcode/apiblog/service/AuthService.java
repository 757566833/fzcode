package com.fzcode.api.blog.service;

import com.fzcode.api.blog.request.AccountRequest;
import com.fzcode.api.blog.response.LoginResponse;
import com.fzcode.internalCommon.dto.GithubUserInfoDTO;
import com.fzcode.internalCommon.dto.ListResponseDTO;
import java.util.List;
import java.util.Map;

public interface AuthService {

    public LoginResponse login(String email, String password);

    public LoginResponse register(String email, String password, String code, Integer registerType);

    public LoginResponse githubRegister(GithubUserInfoDTO githubUserInfo);

    public ListResponseDTO<List<Map<String, Object>>> findAllAccount(AccountRequest accountRequest);

    public Map<String, Object> findByAid(Integer aid);
}
