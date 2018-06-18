package service;

import java.util.List;

import entity.Ground;

public interface IGroundService{
	Ground getGroundById(Integer groundId);
	List<Ground> getGrounds(int pageNum, String text);
}
