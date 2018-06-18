package api;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import entity.User;
import service.IUserService;

@RestController
@RequestMapping("api/user")
public class UserController{
	
	@Autowired
	IUserService userService;
	
	@PutMapping("fcmToken")
	public ResponseEntity<Void> updateToken(@RequestBody Map<String, String> params, UsernamePasswordAuthenticationToken token) {
		String fcmToken = params.get("fcmToken");
		System.out.println(fcmToken);
		if(fcmToken != null){
			int userId = (Integer)token.getPrincipal();
			User user = userService.getUserById(userId);
			user.setFcmToken(fcmToken);
			userService.updateUser(user);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteUser(@RequestBody int userId){
		userService.deleteUser(userId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	
	
	@GetMapping
	public ResponseEntity<User> getUserById(@RequestBody int userId) {
		User user = userService.getUserById(userId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("signUp")
	public ResponseEntity<?> addUser(@RequestHeader String Authorization, @RequestBody Map<String, String> params){
		User user = userService.getUserByToken(Authorization, params);
		if(user != null){
			User saved = userService.addOrUpdateUser(user);
			String jwt = userService.getJWT(saved);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", jwt);
			return new ResponseEntity<User>(saved, headers, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
	}

}