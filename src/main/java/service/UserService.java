package service;

import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import entity.User;
import repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService implements IUserService{
	
	@Value("#{config['signing.key']}")
	String signingKey;
	
	@Autowired
	private UserRepository userRepository;
	
	public User updateUser(User user){
		return userRepository.save(user);
	}
	
	public User getUserById(Integer userId){
		return userRepository.findById(userId).get();
	}
	
	public void deleteUser(int userId){
		userRepository.delete(getUserById(userId));
	}
	
	public synchronized User addOrUpdateUser(User user){
		List<User> list = userRepository.findByEmailAndProvider(user.getEmail(), user.getProvider());
		if(list.size() == 0){
			return userRepository.save(user);
		}else {
			user.setId(list.get(0).getId());
			return userRepository.save(user);
		}
	}
	
	public User getUserByToken(String token, Map<String, String> params){
		String provider = params.get("provider");
		String facebookId = params.get("facebookId");
		if(token == null || provider == null){
			return null;
		}else if(provider.equals("Facebook") && facebookId == null){
			return null;
		}
		RestTemplate restTemplate = new RestTemplate();
		User user = new User();
		String url;
		JSONObject resBody;
		if(provider.equals("Google")){
			url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + token;
			try{
				resBody = restTemplate.getForEntity(url, JSONObject.class).getBody();
				user.setEmail((String)resBody.get("email"));
				user.setName((String)resBody.get("name"));
				user.setPictureUrl((String)resBody.get("picture"));
				user.setProvider(provider);
				return user;
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}else if(provider.equals("Facebook")){
			url = "https://graph.facebook.com/v2.8/"
					+ params.get("facebookId")
					+ "?fields=name,picture&access_token="
					+ token;
			try{
				resBody = restTemplate.getForEntity(url, JSONObject.class).getBody();
				user.setEmail((String)resBody.get("id"));
				user.setName((String)resBody.get("name"));
				Map<String, Map<String, String>> picture = (Map<String, Map<String, String>>) resBody.get("picture");
				Map<String, String> data = picture.get("data");
				user.setPictureUrl((String)data.get("url"));
				user.setProvider(provider);
				return user;
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	public String getJWT(User user) {
		Claims claims = Jwts.claims();
        claims.put("id", user.getId() + "");
        claims.put("role", "USER");
		String jwt = Jwts.builder()
				.setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
		return jwt;
	}
	
}
