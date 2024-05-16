package daeucna.system.user;

import java.util.List;

import lombok.Data;

@Data
public class UserSaveDto {	
	private UserDto searchCond;
	private List<UserDto> saveData;
}
