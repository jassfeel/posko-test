package daeucna.config.security.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import daeucna.config.security.dto.AuthorityDto;
import daeucna.config.security.dto.UserDto;
import daeucna.config.security.entity.Authority;
import daeucna.config.security.entity.User;
import daeucna.config.security.repository.UserRepository;
import daeucna.config.security.utils.CommonJson;
import daeucna.config.security.utils.DuplicateMemberException;
import daeucna.config.security.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Set<Authority> setAuthority = new HashSet<Authority>();
        Set<AuthorityDto> setAuthorityDto = userDto.getAuthorityDtoSet();
        Iterator<AuthorityDto> itAuthorityDto = setAuthorityDto.iterator();
        while(itAuthorityDto.hasNext()) {
        	AuthorityDto curAuthorityDto = itAuthorityDto.next();
        	setAuthority.add(
	    		Authority.builder()
	            .authorityName(curAuthorityDto.getAuthorityName())
	            .build()
	        );

        	log.debug("setAuthority = " + CommonJson.objectToString(setAuthority));
        }
        
        		

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(setAuthority)
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null));
    }
}