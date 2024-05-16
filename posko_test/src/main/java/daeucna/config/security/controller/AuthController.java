package daeucna.config.security.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import daeucna.config.security.dto.LoginDto;
import daeucna.config.security.dto.TokenDto;
import daeucna.config.security.jwt.JwtFilter;
import daeucna.config.security.jwt.JwtTokenProvider;
import daeucna.config.security.utils.CommonJson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(
    			@Valid @RequestBody LoginDto loginDto,
    			@Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
    			@Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds
    		) {

        return fnAuthorize(loginDto, tokenValidityInSeconds, refreshTokenValidityInSeconds, false);
    }
    
    public ResponseEntity<TokenDto> fnAuthorize(LoginDto loginDto, long tokenValidityInSeconds, long refreshTokenValidityInSeconds, boolean blnRefresh) {
	
	    UsernamePasswordAuthenticationToken authenticationToken =
	            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
	
	    // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
	    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
	    // 해당 객체를 SecurityContextHolder에 저장하고
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
	    String jwt = tokenProvider.createToken(authentication, tokenValidityInSeconds, loginDto);
	    String jwtRefresh = tokenProvider.createToken(authentication, refreshTokenValidityInSeconds, loginDto);
	    //요청이 RefreshToken 아닐 경우만 생성
	    if (blnRefresh) jwtRefresh = null;
	
	    HttpHeaders httpHeaders = new HttpHeaders();
	    // response header에 jwt token에 넣어줌
	    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
	
	    // tokenDto를 이용해 response body에도 넣어서 리턴
	    log.debug("jwt = " + jwt);
	    log.debug("jwtRefresh = " + jwtRefresh);
	    return new ResponseEntity<>(new TokenDto(jwt, jwtRefresh), httpHeaders, HttpStatus.OK);
	}    
    @PostMapping("/refreshtoken")
    public ResponseEntity<TokenDto> refreshToken(
    			@RequestHeader HttpHeaders header,
    			@Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
    			@Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds
    		) {

    	String sRefreshToken = header.getFirst(JwtFilter.AUTHORIZATION_REFRESH_HEADER);
    	LoginDto loginDto = null;
    	if (tokenProvider.validateToken(sRefreshToken)) {
    		Jws<Claims> claims = tokenProvider.getClaims(sRefreshToken);
    		
            log.debug("parseJwt = " + CommonJson.objectToString(claims));
            log.debug("parseJwt(getSubject) = " + claims.getPayload().getSubject());
            log.debug("parseJwt(auth) = " + (String) claims.getPayload().get("auth"));
            
            HashMap<String, String> hmLoginDto = claims.getPayload().get("loginDto", HashMap.class);
            loginDto = LoginDto.builder()
            		.username(hmLoginDto.get("username"))
            		.password(hmLoginDto.get("password"))
            		.build();
            log.debug("parseJwt(LoginDto) = " + CommonJson.objectToString(loginDto));    		
    	}
    	
        return fnAuthorize(loginDto, tokenValidityInSeconds, refreshTokenValidityInSeconds, true);
    }    
}