package com.fzcode.authservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class AccountDTOFlowServiceApplicationTests {

    @Test
    void contextLoads() {
//		AccountService accountService = new AccountService();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String oldStr = bCryptPasswordEncoder.encode("test");
        System.out.println(oldStr);
//        bCryptPasswordEncoder.matches("test", oldStr);
        System.out.println( bCryptPasswordEncoder.matches("test", oldStr));
//		accountService.c();
    }

}
