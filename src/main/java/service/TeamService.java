package service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Member;
import entity.Team;
import entity.User;
import repository.MemberRepository;
import repository.TeamRepository;

@Service
public class TeamService implements ITeamService{
	
	@Autowired
	private TeamRepository teamRepository;
	
	
	@Autowired
	private MemberRepository memberRepository;
		
	public void updateTeam(Team team){
		teamRepository.save(team);
	}
	
	public void deleteTeam(int articleId){
		teamRepository.delete(getTeamById(articleId));
	}
	
	public Team getTeamById(Integer teamId){
		return teamRepository.findById(teamId).get();
	}
	
	public boolean checkExistByName(String name){
		List<Team> teamList = teamRepository.findByName(name);
		if(teamList.size() > 0) {
			return true;
		}else {
			return false;
		}
	}

	public synchronized Team addTeam(Team team, int userId){
		List<Team> tList = teamRepository.findByName(team.getName()); 	
		if(tList.size() > 0){
			return null;
		}
		List<Member> mList = memberRepository.findByUserId(userId);
        if(mList.size() >= 3){
           return null;
        }
    	return teamRepository.save(team);
        
	}
	
	public List<Team> getMyTeams(int userId){
		User user = new User();
		user.setId(userId);
		return memberRepository.getMyTeams(user);
	}
	
}
