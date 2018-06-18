package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import entity.Member;
import entity.Team;
import entity.User;
import service.IMemberService;
import service.ITeamService;
import service.IUserService;


@RestController
@RequestMapping("api/team")
public class TeamController{
	
	@Autowired
	ITeamService teamService;
	     	    
	@Autowired
	IMemberService memberService;
	
	@Autowired
	IUserService userService;
	
	@GetMapping("checkExist")
	public ResponseEntity<Boolean> checkExsist(@RequestParam String name) {
		boolean exist = teamService.checkExistByName(name);
		return new ResponseEntity<Boolean>(exist , HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> addTeam(UsernamePasswordAuthenticationToken token, @RequestBody Team team){
		int userId = (Integer)token.getPrincipal();
		Team savedTeam = teamService.addTeam(team, userId);
        if(savedTeam == null){
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        User savedUser = userService.getUserById(userId);
        Member member = new Member();
        member.setTeam(savedTeam);
        member.setUser(savedUser);
        member.setRole("Manager");
        memberService.addMember(member);
        return new ResponseEntity<Team>(savedTeam, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Team>> getMyTeams(UsernamePasswordAuthenticationToken token) {
		int userId = (Integer)token.getPrincipal();
		List<Team> list = teamService.getMyTeams(userId);
		return new ResponseEntity<List<Team>>(list , HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Void> updateTeam(@RequestBody Team team) {
		teamService.updateTeam(team);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteTeam(@PathVariable("id") Integer id) {
		teamService.deleteTeam(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	

}