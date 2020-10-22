package com.fzcode.authservice;

import com.fzcode.authservice.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("aaa");
		String sss = JwtUtils.createToken("dsadsa");
		System.out.println(sss);

		System.out.println("sss");
//		System.out.println(sss);
	}

}
