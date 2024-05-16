package daeucna.mapper.primary.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import daeucna.system.user.UserAuthorityDto;
import daeucna.system.user.UserDto;

@Mapper
public interface UserMapper {
   List<UserDto> getUsers(UserDto param);
   int saveUser(UserDto param);   
   void deleteUser(UserDto param);
   
   List<UserAuthorityDto> getUsersAuthority(UserAuthorityDto param);
   int saveUserAuthority(UserAuthorityDto param);   
   void deleteUserAuthority(UserAuthorityDto param);

   List<Map> getUserList(Map param);
   List<Map> getAuthorityList(Map param);
   
}