package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import entity.Matching;
import entity.Team;
import repository.MatchingRepository;
import repository.TeamRepository;

@Service
public class MatchingService implements IMatchingService{
	
	@Autowired
	private MatchingRepository matchingRepository;
	
	@Autowired
	private TeamRepository teamRepository;
		
	public void updateMatching(Matching matching){
		matchingRepository.save(matching);
	}
	
	public void deleteMatching(int articleId){
		matchingRepository.delete(getMatchingById(articleId));
	}
	
	public Matching getMatchingById(Integer matchingId){
		return matchingRepository.findById(matchingId).get();
	}
	
	public List<Matching> getMatches(int pageNum){
		return matchingRepository.getMatches(PageRequest.of((pageNum-1)*20, 20));
	}
	

	public synchronized boolean addMatching(Matching matching){
		int numOfDuplicated = matchingRepository.getNumOfDuplicated(matching.getStart(), matching.getEnd(), new Integer(matching.getGround().getId())); 	
        if(numOfDuplicated > 0){
           return false;
        }else{
        	matchingRepository.save(matching);
        	return true;
        }
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------------------------------
	
	public List<Matching> getRelatedMatches(int teamId, int pageNum){
		return matchingRepository.getRelatedMatches(teamId, PageRequest.of((pageNum-1)*20, 20));
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------------------------------
	
	public void complete(int matchingId, int teamId){
		Matching matching = matchingRepository.getOne(matchingId);
		Team awayTeam = teamRepository.getOne(teamId);
		matching.setAwayTeam(awayTeam);
		matchingRepository.save(matching);
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------------------------------
	
	public Matching checkCompleted(int matchingId){
		Matching matching = matchingRepository.getOne(matchingId);
		if(matching.getAwayTeam() == null){
			return matching;
		}else{
			return null;
		}
	}
	
}
