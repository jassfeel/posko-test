package daeucna.system.user;

import java.util.List;
import java.util.Map;

public interface UserService {
	
	@SuppressWarnings("rawtypes")	
    public List<UserDto> getUsers(UserDto param);

	@SuppressWarnings("rawtypes")	
    public List<UserDto> saveUsers(UserSaveDto params);

	
	@SuppressWarnings("rawtypes")	
    public Map<String, Object> getUsersAuthority(UserAuthorityDto param);

	@SuppressWarnings("rawtypes")	
    public Map<String, Object> saveUsersAuthority(UserAuthoritySaveDto params);
	
}