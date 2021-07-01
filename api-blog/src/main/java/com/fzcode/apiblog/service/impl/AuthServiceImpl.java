//package com.fzcode.apiblog.service.impl;
//
//import com.fzcode.apiblog.request.AccountRequest;
//import com.fzcode.apiblog.response.LoginResponse;
//import com.fzcode.apiblog.service.AuthService;
//import com.fzcode.internalCommon.dto.GithubUserInfoDTO;
//import com.fzcode.internalCommon.dto.ListResponseDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//
//import java.util.List;
//import java.util.Map;
//
//public class AuthServiceImpl implements AuthService {
////    private static WebClient webClient;
////    @Autowired
////    public void setWebClient() {
////        AuthServiceImpl.webClient = WebClient.create(services.getHost().get("mail"));
////    }
//    @Autowired
//    private DiscoveryClient discoveryClient;
//
//    @Override
//    public LoginResponse login(String email, String password) {
//        List<ServiceInstance> list =  discoveryClient.getInstances("ServiceName.SERVICE_AUTH");
//        Integer integer = list.size();
//        Double d = Math.random()*100;
//        ServiceInstance serviceInstance =  list.get(d.intValue()%integer);
//
//        serviceInstance.getUri();
//        System.out.println(serviceInstance.getUri());
//        System.out.println(    serviceInstance.getHost());
//        return null;
//    }
//
//    @Override
//    public LoginResponse register(String email, String password, String code, Integer registerType) {
//        return null;
//    }
//
//    @Override
//    public LoginResponse githubRegister(GithubUserInfoDTO githubUserInfo) {
//        return null;
//    }
//
//    @Override
//    public ListResponseDTO<List<Map<String, Object>>> findAllAccount(AccountRequest accountRequest) {
//        return null;
//    }
//
//    @Override
//    public Map<String, Object> findByAid(Integer aid) {
//        return null;
//    }
//}
