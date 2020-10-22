package com.fzcode.authservice;

import com.fzcode.authservice.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("aaa");
		String sss = JwtUtils.parseToken("eyJlbWFpbCI6Ijc1NzU2NjgzM0BxcS5jb20iLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3NTc1NjY4MzNAcXEuY29tIiwiaWF0IjoxNjAzMzQyMzQ2LCJleHAiOjE2MDM2MDE1NDZ9.pgC6Bg6i4C9JXoplYAy3qvOo2-BI9azJcYeLJVBE4kp8isJR3L-M7v3IwNevVbBp3Fl-QH5H1afWiUs7hKGm-g");
		System.out.println(sss);

		System.out.println("sss");
//		System.out.println(sss);
	}

}
