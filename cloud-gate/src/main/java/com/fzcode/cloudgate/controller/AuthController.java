//package com.fzcode.cloudgate.controller;
//
//import com.fzcode.cloudgate.util.RedisUtils;
//import com.fzcode.internalcommon.dto.common.AuthorityDTO;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController()
//@RequestMapping(value = "/pri",consumes = MediaType.APPLICATION_JSON_VALUE)
//public class AuthController {
//    @GetMapping(value = "/refresh/authority")
//    public void refreshAuthority(@RequestBody AuthorityDTO authorityDTO) {
//        RedisUtils.setHash("authority", authorityDTO.getAccount(), authorityDTO.getAuthority());
//    }
//}
