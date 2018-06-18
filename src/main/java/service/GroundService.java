package service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import entity.Ground;
import entity.Matching;
import repository.GroundRepository;

@Service
public class GroundService implements IGroundService{
	
	@Autowired
	private GroundRepository groundRepository;
		
	
	public Ground getGroundById(Integer groundId){
		return groundRepository.findById(groundId).get();
	}
	
	
	public List<Ground> getGrounds(int pageNum, String text){
		return groundRepository.getGrounds(PageRequest.of((pageNum-1)*20, 20), text);
	}
	
}
