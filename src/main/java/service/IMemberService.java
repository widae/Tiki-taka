package service;

import java.util.List;

import entity.Member;

public interface IMemberService{
	void updateMember(Member member);
	void deleteMember(int articleId);
	Member getMemberById(Integer memberId);
	List<Member> getMembersByUserId(int userId);
	List<Member> getTeamMembers(int teamId);
	Member addMember(Member member);
}
