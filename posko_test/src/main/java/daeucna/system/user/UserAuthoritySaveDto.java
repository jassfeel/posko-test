package daeucna.system.user;

import java.util.List;

import lombok.Data;

@Data
public class UserAuthoritySaveDto {	
	private UserAuthorityDto searchCond;
	private List<UserAuthorityDto> saveData;
}
