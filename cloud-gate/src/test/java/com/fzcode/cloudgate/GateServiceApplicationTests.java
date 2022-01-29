package com.fzcode.cloudgate;

import com.fzcode.cloudgate.util.TokenUtils;
import com.fzcode.internalcommon.dto.common.TokenInfoDTO;
import com.fzcode.internalcommon.utils.JSONUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class GateServiceApplicationTests {
	@Autowired
	TokenUtils tokenUtils;

	@Test
	void parseBearer() {
		String token  = "bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NDM0NDk2NjAsImV4cCI6MTY0MzcwODg2MCwiYWlkIjoiYWlkMSIsImVtYWlsIjoiZW1haWwxIiwidWlkIjoidWlkMSJ9.x994v7acmgraMEq1KyA1TLV5NSt8UgRXB6lLJRsJftU";
		int preIndex = token.indexOf("bearer ");
		if(preIndex==-1){
			throw new JwtException("token格式不对");
		}
		String jwsStr = token.substring(7);
		TokenInfoDTO tokenInfoDTO = null;
		try {
			Claims claims = Jwts.parserBuilder()
					.setSigningKey( Keys.hmacShaKeyFor(Decoders.BASE64.decode("ko3678678576fjdksljfdlksjfkldsjflkdsjflksdjlkfjdslkfjldksjfkldsjflkds56756756756757656754610gate0docker")))
					.build()
					.parseClaimsJws(jwsStr)
					.getBody();

					System.out.println(claims.get("uid"));
			System.out.println(claims.get("email"));
			System.out.println(claims.get("aid"));
			;
		}catch (ExpiredJwtException e){
//			e.getClaims().get
			System.out.println(e.getMessage());
			System.out.println(e.getClaims());
			System.out.println(JSONUtils.stringify(e.getClaims()));
		}



        System.out.println(JSONUtils.stringify(tokenInfoDTO));
	}
	@Test
	void createBearer() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, 3);
		TokenInfoDTO tokenInfoDTO = new TokenInfoDTO();
		tokenInfoDTO.setAid("aid");
		tokenInfoDTO.setEmail("email");
		tokenInfoDTO.setUid("uid");
		tokenInfoDTO.setIssuedAt( new Date());
		tokenInfoDTO.setExpiration(c.getTime());
		String compactJws = Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode("ko3678678576fjdksljfdlksjfkldsjflkdsjflksdjlkfjdslkfjldksjfkldsjflkds56756756756757656754610gate0docker")), SignatureAlgorithm.HS256)
				.setClaims(tokenInfoDTO)
				.claim("aid","aid1")
				.claim("email","email1")
				.claim("uid","uid1")
				.compact();
		System.out.println("bearer "+compactJws);

	}
}
