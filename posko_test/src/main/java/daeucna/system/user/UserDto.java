package daeucna.system.user;

import lombok.Data;

@Data
public class UserDto {	
	private String rowStatus;

    private Long userId;
    private String username;
    private boolean activated;
    private String nickname;
    private String password;
}
