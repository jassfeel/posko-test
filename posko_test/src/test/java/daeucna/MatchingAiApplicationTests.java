package daeucna;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import daeucna.config.security.dto.AuthorityDto;
import daeucna.config.security.dto.LoginDto;
import daeucna.config.security.dto.UserDto;
import daeucna.config.security.jwt.JwtFilter;
import daeucna.config.security.utils.CommonJson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ContextConfiguration(classes = MatchingAiApplication.class)
@AutoConfigureMockMvc
@Slf4j
class MatchingAiApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void userSignup() throws Exception {
		AuthorityDto authorityDto = AuthorityDto.builder().authorityName("ROLE_ADMIN").build();
		UserDto userDto = UserDto.builder()
				.username("sangkiTest")
				.password("sangkiTest")
				.nickname("sangkiTest")
				.authorityDtoSet(new HashSet<AuthorityDto>( Arrays.asList(authorityDto) ))
				.build();
		
		String body = CommonJson.objectToString(userDto);
		this.mockMvc.perform(
					post("/api/signup")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("sangkiTest")));
	}	

	@Test
	void authenticate() throws Exception {
		fnAuthenticate();
	}
	
	MvcResult fnAuthenticate() throws Exception {
		LoginDto loginDto = LoginDto.builder()
				.username("sangkiTest")
				.password("sangkiTest")
				.build();
		
		String body = CommonJson.objectToString(loginDto);
		MvcResult mvcResult = this.mockMvc.perform(
					post("/api/authenticate")
					.contentType(MediaType.APPLICATION_JSON)
					.content(body)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("token")))
				.andReturn();
		
		return mvcResult;
	}	
	
	@SuppressWarnings("unchecked")
	@Test
	void user() throws Exception {
		MvcResult mvcResult = fnAuthenticate();
		String sReturn = mvcResult.getResponse().getContentAsString();
		log.debug("sReturn = " + sReturn);
		
		HashMap<String, String> mapVal = (HashMap<String, String>) CommonJson.stringToObject(sReturn, HashMap.class);
		log.debug("token = " + mapVal.get("token") );
		
		this.mockMvc.perform(
				get("/api/user")
				.header("Authorization", "Bearer " + mapVal.get("token"))
			)
			.andDo(print())
			.andExpect(status().isOk());
		
	}

	@SuppressWarnings("unchecked")
	@Test
	void testRedirect() throws Exception {
		MvcResult mvcResult = fnAuthenticate();
		String sReturn = mvcResult.getResponse().getContentAsString();
		log.debug("sReturn = " + sReturn);
		
		HashMap<String, String> mapVal = (HashMap<String, String>) CommonJson.stringToObject(sReturn, HashMap.class);
		log.debug("token = " + mapVal.get("token") );
		
		this.mockMvc.perform(
				post("/api/test-redirect")
				.header("Authorization", "Bearer " + mapVal.get("token"))
			)
			.andDo(print())
			.andExpect(status().isOk());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void userSangki() throws Exception {
		MvcResult mvcResult = fnAuthenticate();
		String sReturn = mvcResult.getResponse().getContentAsString();
		log.debug("sReturn = " + sReturn);
		
		HashMap<String, String> mapVal = (HashMap<String, String>) CommonJson.stringToObject(sReturn, HashMap.class);
		log.debug("token = " + mapVal.get("token") );
		
		this.mockMvc.perform(
				get("/api/user/sangki")
				.header("Authorization", "Bearer " + mapVal.get("token"))
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("sangki")));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void hello() throws Exception {
		MvcResult mvcResult = fnAuthenticate();
		String sReturn = mvcResult.getResponse().getContentAsString();
		log.debug("sReturn = " + sReturn);
		
		HashMap<String, String> mapVal = (HashMap<String, String>) CommonJson.stringToObject(sReturn, HashMap.class);
		log.debug("token = " + mapVal.get("token") );
		
		this.mockMvc.perform(
				get("/api/hello")
				.header("Authorization", "Bearer " + mapVal.get("token"))
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("hello")));
		
	}
	
	@Test
	void parseJwt(@Value("${jwt.secret}") String secret) throws Exception {
		MvcResult mvcResult = fnAuthenticate();
		String sReturn = mvcResult.getResponse().getContentAsString();

		HashMap<String, String> mapVal = (HashMap<String, String>) CommonJson.stringToObject(sReturn, HashMap.class);
		String accessToken =  (String) mapVal.get("token");
        byte[] keyBytes = Decoders.BASE64URL.decode(secret);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);		
    	Jws<Claims> claims;
    	try{
            claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(accessToken);
            log.debug("parseJwt = " + CommonJson.objectToString(claims));
            log.debug("parseJwt(getSubject) = " + claims.getBody().getSubject());
            log.debug("parseJwt(claims) = " + CommonJson.objectToString(claims.getBody()));
        } catch (Exception ignored) {
            log.debug("parseJwt = " + ignored.getLocalizedMessage());
        }    	
	}
	
	@Test
	void refreshToken() throws Exception {

		MvcResult mvcResult = fnAuthenticate();
		String sReturn = mvcResult.getResponse().getContentAsString();

		HashMap<String, String> mapVal = (HashMap<String, String>) CommonJson.stringToObject(sReturn, HashMap.class);
		String refreshToken =  (String) mapVal.get("refreshToken");
		
		this.mockMvc.perform(
					post("/api/refreshtoken")
					.header(JwtFilter.AUTHORIZATION_REFRESH_HEADER, refreshToken)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("token")))
				.andReturn();
	}	

}
