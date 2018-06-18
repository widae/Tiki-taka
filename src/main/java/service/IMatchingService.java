package service;

import java.util.List;

import entity.Matching;

public interface IMatchingService{
	void updateMatching(Matching matching);
	void deleteMatching(int articleId);
	Matching getMatchingById(Integer matchingId);
	List<Matching> getMatches(int pageNum);
	boolean addMatching(Matching matching);
	List<Matching> getRelatedMatches(int teamId, int pageNum);
	void complete(int matchingId, int teamId);
	Matching checkCompleted(int matchingId);
}
