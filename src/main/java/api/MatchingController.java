package api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import entity.Ground;
import entity.Matching;
import entity.Team;
import service.IGroundService;
import service.IMatchingService;
import service.ITeamService;


@RestController
@RequestMapping("api/matching")
public class MatchingController{
	
	@Autowired
	IMatchingService matchingService;
	
	@Autowired
	ITeamService teamService;
	
	@Autowired
	IGroundService groundService;
	
	/* can be deleted
    @GetMapping("{id}")
	public ResponseEntity<Matching> getMatchingById(@PathVariable Integer id) {
		Matching matching = matchingService.getMatchingById(id);
		return new ResponseEntity<Matching>(matching , HttpStatus.OK);
	}
	*/
	
	@GetMapping
	public ResponseEntity<List<Matching>> getMatches(@RequestParam int pageNum){
		List<Matching> list = matchingService.getMatches(pageNum);
		return new ResponseEntity<List<Matching>>(list , HttpStatus.OK);
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------------------------------
	
	@GetMapping("related")
	public ResponseEntity<List<Matching>> getCurRelatedMatches(@RequestParam int teamId, @RequestParam int pageNum){
		List<Matching> list = matchingService.getRelatedMatches(teamId, pageNum);
		return new ResponseEntity<List<Matching>>(list, HttpStatus.OK);
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------------------------------
	

	@PostMapping
	public ResponseEntity<Void> addMatching(@RequestBody Matching matching){
		/*--- create ---*/
		boolean flag = matchingService.addMatching(matching);
		/*--- response ---*/
		if(flag != false){
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("complete")
	public ResponseEntity<Void> updateMatching(@RequestBody Map<String, Integer> map) {
		Integer matchingId = map.get("matchingId");
		Integer teamId = map.get("teamId");
		if(matchingId == null || teamId == null){
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		matchingService.complete(matchingId, teamId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteMatching(@PathVariable("id") Integer id) {
		matchingService.deleteMatching(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	
	
	@ExceptionHandler(Exception.class)
	public void handleAllException(Exception ex) {
		StackTraceElement[] elements = ex.getStackTrace();
		ex.printStackTrace();
		for(int i = 0; i < 500; i++){
			System.out.println(elements[i]);
		}
	}


}