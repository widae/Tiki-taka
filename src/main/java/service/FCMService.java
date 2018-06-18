package service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import entity.Matching;
import entity.Member;
import entity.Team;
import entity.User;
import utility.HeaderRequestInterceptor;

@Service
public class FCMService {
 
	@Value("#{config['FIREBASE_SERVER_KEY']}")
	String FIREBASE_SERVER_KEY;
	
	@Autowired
	IMemberService memberService;
	
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	
	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity) {
 
		RestTemplate restTemplate = new RestTemplate();
 
		/**
		https://fcm.googleapis.com/fcm/send
		Content-Type:application/json
		Authorization:key=FIREBASE_SERVER_KEY*/
 
		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);
 
		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
 
		return CompletableFuture.completedFuture(firebaseResponse);
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------------------------------

	public HttpEntity<String> getRequest(Matching possible) throws UnsupportedEncodingException{
		
		JSONObject body = new JSONObject();
		
		int homeTeamId = possible.getHomeTeam().getId();
		String token = getToken(homeTeamId);
		
		body.put("to", token);
		body.put("priority", "high");
		body.put("notification", getNotification(possible));
		
		/*JSONObject custom = new JSONObject();
		custom.put("selectedTeam", possible.getHomeTeam());
		custom.put("matchingId", possible.getId());
		
		body.put("my_custom_data", custom);*/
		
		return new HttpEntity<String>(body.toString());
		
	}
	
	// 수정되야 한다
	public String getToken(int homeTeamId){
		List<Member> members = memberService.getTeamMembers(homeTeamId);
		User user = members.get(0).getUser();
		return user.getFcmToken();
	}
	
	public JSONObject getNotification(Matching possible) throws UnsupportedEncodingException{
		
		/*--- set title ---*/
		Team team = possible.getAwayTeam();
		String title = URLEncoder.encode(team.getName() + "가(이) 매칭을 신청했습니다.", "UTF-8");
		
		/*--- set message ---*/
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(possible.getStart());
		String groundName = possible.getGround().getName();
		String message = URLEncoder.encode(date + ", " + groundName, "UTF-8");
		
		/*--- put ---*/
		JSONObject notification = new JSONObject();
		notification.put("title", title);
		notification.put("body", message);
		
		return notification;
		
	}
	
}