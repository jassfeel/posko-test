package daeucna.config.security;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import daeucna.config.security.dto.AuthorityDto;
import daeucna.config.security.dto.UserDto;
import daeucna.config.security.service.UserService;

@Component
public class UserInitializer implements ApplicationRunner {

	private final UserService userService;
	private final Environment env;

    @Autowired
    public UserInitializer(Environment env, UserService userService) {
    	this.userService = userService;
    	this.env = env;
    }

    @Override
    public void run(ApplicationArguments args) {
    	//초기 Admin User 넣어주기
		AuthorityDto authorityDto = AuthorityDto.builder().authorityName("ROLE_ADMIN").build();
		UserDto userDto = UserDto.builder()
				.username("sangkiham")
				.password("sangkiham")
				.nickname("sangkiham")
				.authorityDtoSet(new HashSet<AuthorityDto>( Arrays.asList(authorityDto) ))
				.build();

		//사용자 정보가 없으면 Insert한다.
		boolean blnAdminInit = Boolean.parseBoolean(env.getProperty("admin.init"));
    	if (blnAdminInit && userService.getUserWithAuthorities(userDto.getUsername()) == null) {
    		userService.signup(userDto);
    	}
   }
}