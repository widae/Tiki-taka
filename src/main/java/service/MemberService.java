package service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Member;
import repository.MemberRepository;

@Service
public class MemberService implements IMemberService{
	
	@Autowired
	private MemberRepository memberRepository;
		
	public void updateMember(Member member){
		memberRepository.save(member);
	}
	
	public void deleteMember(int articleId){
		memberRepository.delete(getMemberById(articleId));
	}
	
	public Member getMemberById(Integer memberId){
		return memberRepository.findById(memberId).get();
	}
	
	public List<Member> getMembersByUserId(int userId){
		return memberRepository.findByUserId(userId);
	}
	
	public List<Member> getTeamMembers(int teamId){
		return memberRepository.findByTeamId(teamId);
	}
	
	public synchronized Member addMember(Member member){
		List<Member> list = memberRepository.findByUserIdAndTeamId(member.getUser().getId(), member.getTeam().getId()); 	
        if(list.size() > 0){
           return null;
        }else{
        	return memberRepository.save(member);
        }
	}
	
}
