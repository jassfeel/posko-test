package daeucna.system.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import daeucna.mapper.primary.system.UserMapper;
import daeucna.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Repository
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public List<UserDto> getUsers(UserDto param) {
		List<UserDto> lUserDto = new ArrayList<UserDto>();

		lUserDto = userMapper.getUsers(param);

		log.info("getUsers");
		return lUserDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDto> saveUsers(UserSaveDto params) {

		// 삭제처리먼저
		for (UserDto curRec : params.getSaveData()) {
			String sRowStatus = curRec.getRowStatus();
			if ("D".indexOf(sRowStatus) > -1) {
				// 사용자
				userMapper.deleteUser(curRec);
			}
		}
		// 신규및 업데이트 처리
		for (UserDto curRec : params.getSaveData()) {
			String sRowStatus = curRec.getRowStatus();
			if ("N,U".indexOf(sRowStatus) > -1) {
				// 사용자
				// 패스워드를 암호화
				String sEncodedPassword = null;
				String sOrgPassword = StringUtil.defaultIfEmpty(curRec.getPassword(), null);
				if (sOrgPassword != null) sEncodedPassword = passwordEncoder.encode(sOrgPassword);
				curRec.setPassword(sEncodedPassword);
				userMapper.saveUser(curRec);
			}
		}

		List<UserDto> lUserDto = userMapper.getUsers(params.getSearchCond());

		log.info("saveUsers");
		return lUserDto;
	}

	@Override
	public Map<String, Object> getUsersAuthority(UserAuthorityDto param) {
		Map<String, Object> rtnVal = new HashMap<String, Object>();

		List<UserAuthorityDto> lUserAuthority = userMapper.getUsersAuthority(param);
		rtnVal.put("userAuthority", lUserAuthority);
		
		List<Map> lmUserList = userMapper.getUserList(new HashMap<String, String>());
		rtnVal.put("userList", lmUserList);

		List<Map> lmAuthorityList = userMapper.getAuthorityList(new HashMap<String, String>());
		rtnVal.put("authorityList", lmAuthorityList);

		log.info("getUsersAuthority");
		return rtnVal;
	}

	@Override
	public Map<String, Object> saveUsersAuthority(UserAuthoritySaveDto params) {
		
		// 삭제처리먼저
		for (UserAuthorityDto curRec : params.getSaveData()) {
			String sRowStatus = curRec.getRowStatus();
			if ("D".indexOf(sRowStatus) > -1) {
				// 사용자
				userMapper.deleteUserAuthority(curRec);
			}
		}
		// 신규및 업데이트 처리
		for (UserAuthorityDto curRec : params.getSaveData()) {
			String sRowStatus = curRec.getRowStatus();
			if ("N,U".indexOf(sRowStatus) > -1) {
				// 사용자
				userMapper.saveUserAuthority(curRec);
			}
		}

		Map<String, Object> rtnVal = getUsersAuthority(params.getSearchCond());

		log.info("saveUsersAuthority");
		return rtnVal;
	}

}