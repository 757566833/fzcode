package com.fzcode.gateservice;

import com.fzcode.gateservice.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GateServiceApplicationTests {

	@Test
	void contextLoads() {
		RedisUtils.setString("aaa","bbb").subscribe(System.out::println);
	}

}
