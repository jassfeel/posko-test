package daeucna.system.user;

import lombok.Data;

@Data
public class UserAuthorityDto {	
	private String rowStatus;

    private Long userId;
    private String username;
    private String authorityName;
}
