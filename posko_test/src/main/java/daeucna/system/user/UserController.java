package daeucna.system.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
	@Autowired
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers(@RequestBody UserDto param) {
    	List<UserDto> rtnVal = new ArrayList<UserDto>();
    	
    	rtnVal = userService.getUsers(param);
    	
        return ResponseEntity.ok(rtnVal);
    }

    @PostMapping("/saveUsers")
    public ResponseEntity<List<UserDto>> saveUsers(@RequestBody UserSaveDto params) {
    	
    	List<UserDto> rtnVal = new ArrayList<UserDto>();
    	
    	rtnVal = userService.saveUsers(params);
    	
        return ResponseEntity.ok(rtnVal);
    }

    

    @PostMapping("/usersAuthority")
    public ResponseEntity<Map<String, Object>> usersAuthority(@RequestBody UserAuthorityDto param) {
    	Map<String, Object> rtnVal = new HashMap<String, Object>();
    	
    	rtnVal = userService.getUsersAuthority(param);
    	
        return ResponseEntity.ok(rtnVal);
    }

    @PostMapping("/saveUsersAuthority")
    public ResponseEntity<Map<String, Object>> saveUsersAuthority(@RequestBody UserAuthoritySaveDto params) {
    	
    	Map<String, Object> rtnVal = new HashMap<String, Object>();
    	
    	rtnVal = userService.saveUsersAuthority(params);
    	
        return ResponseEntity.ok(rtnVal);
    }
    
}