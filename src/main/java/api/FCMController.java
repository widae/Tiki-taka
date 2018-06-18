package api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entity.Apply;
import entity.Matching;
import entity.Member;
import entity.Team;
import repository.ApplyRepository;
import service.ApplyService;
import service.FCMService;
import service.IMatchingService;
import service.IMemberService;

@RestController
@RequestMapping("api/fcm")
public class FCMController {

	
	@Autowired
	FCMService fcmService;
	
	@Autowired
	ApplyService applyService;
	
	@Autowired
	IMatchingService matchingService;
 
	@PostMapping("notifyApply")
	public ResponseEntity<Void> notifyApply(@RequestBody Matching possible){
		
		Matching matching = matchingService.checkCompleted(possible.getId());
		if(matching == null){
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

		try{
			HttpEntity<String> request = fcmService.getRequest(possible);
			boolean flag = applyService.addApply(matching, possible.getAwayTeam());
			if(flag == false){
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			String firebaseResponse = fcmService.send(request).join();
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}