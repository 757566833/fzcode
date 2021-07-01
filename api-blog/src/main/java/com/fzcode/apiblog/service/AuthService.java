//package com.fzcode.apiblog.service;
//
//import com.fzcode.apiblog.request.AccountRequest;
//import com.fzcode.apiblog.response.LoginResponse;
//import com.fzcode.internalCommon.dto.GithubUserInfoDTO;
//import com.fzcode.internalCommon.dto.ListResponseDTO;
//import java.util.List;
//import java.util.Map;
//
//public interface AuthService {
//
//    public LoginResponse login(String email, String password);
//
//    public LoginResponse register(String email, String password, String code, Integer registerType);
//
//    public LoginResponse githubRegister(GithubUserInfoDTO githubUserInfo);
//
//    public ListResponseDTO<List<Map<String, Object>>> findAllAccount(AccountRequest accountRequest);
//
//    public Map<String, Object> findByAid(Integer aid);
//}
