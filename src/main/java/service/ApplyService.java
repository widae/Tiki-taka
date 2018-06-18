package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import entity.Apply;
import entity.Matching;
import entity.Team;
import repository.ApplyRepository;

@Service
public class ApplyService {
	
	@Autowired
	ApplyRepository applyRepository;
	
	// 수정될 수 있음
	public boolean addApply(Matching matching, Team team){
		List<Apply> list = 
				applyRepository.findByMatchingIdAndTeamId(matching.getId(), team.getId());
		
		if(list.size() > 0){
			return false; 
		}
				
		Apply apply = new Apply();
		apply.setMatching(matching);
		apply.setTeam(team);
		applyRepository.save(apply);
		
		return true;
		
	}

}
