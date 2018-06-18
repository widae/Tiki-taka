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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import entity.Member;
import service.IMemberService;


@RestController
@RequestMapping("api/member")
public class MemberController{
	
	@Autowired
	IMemberService memberService;
	 
	@GetMapping
	public ResponseEntity<List<Member>> getMembersByUserId(UsernamePasswordAuthenticationToken token){
		int userId = (Integer)token.getPrincipal();
		List<Member> list = memberService.getMembersByUserId(userId);
		return new ResponseEntity<List<Member>>(list , HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Member> updateMember(@RequestBody Member member) {
		memberService.updateMember(member);
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable("id") Integer id) {
		memberService.deleteMember(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	

}