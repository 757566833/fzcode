package com.fzcode.authservice;

import com.fzcode.authservice.util.JwtUtils;
import com.fzcode.authservice.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthFlowServiceApplicationTests {

	@Test
	void contextLoads() {
		RedisUtils.setString("aaa3","ccc",600);
		String dddd = RedisUtils.getString("aaa2");
		System.out.println(dddd);
	}

}
