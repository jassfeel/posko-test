package daeucna.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import daeucna.config.security.jwt.JwtAccessDeniedHandler;
import daeucna.config.security.jwt.JwtAuthenticationEntryPoint;
import daeucna.config.security.jwt.JwtFilter;
import daeucna.config.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // PasswordEncoder는 BCryptPasswordEncoder를 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        		// token을 사용하는 방식이기 때문에 csrf를 disable합니다.
		        .csrf(AbstractHttpConfigurer::disable)
		        .securityMatcher("/api/**")
		        .authorizeHttpRequests((authorizeHttpRequests) ->
	 				authorizeHttpRequests
			            .requestMatchers("/api/admin/**").hasRole("ADMIN")
		                .requestMatchers("/api/authenticate").permitAll() // 로그인 api
		                .requestMatchers("/api/refreshtoken").permitAll() // Refresh Token api
		                .requestMatchers("/api/signup").permitAll() // 회원가입 api
		                .requestMatchers("/api/exceptionDenied").permitAll() // Exception Denied		
//		                .anyRequest().permitAll()
		                .anyRequest().authenticated() // 그 외 인증 없이 접근X
	 			)
		        .exceptionHandling(exceptionHandling -> exceptionHandling
		        		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
	 					.accessDeniedPage("/api/exceptionDenied")
	 					.accessDeniedHandler(jwtAccessDeniedHandler)
				)
	 			.formLogin(Customizer.withDefaults())
		        .headers((headers) ->
					headers
						.frameOptions(frameOptions -> frameOptions
			                     .sameOrigin()
			            )
				)
                // 세션을 사용하지 않기 때문에 STATELESS로 설정
		        .sessionManagement((sessionManagement) ->
					sessionManagement
		                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		        )
		        .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
//                .apply(new JwtSecurityConfig(tokenProvider)); // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용

        return httpSecurity.build();
    }

}