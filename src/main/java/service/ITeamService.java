package service;

import java.util.List;

import entity.Team;

public interface ITeamService{
	void updateTeam(Team team);
	void deleteTeam(int teamId);
	Team getTeamById(Integer teamId);
	boolean checkExistByName(String name);
	Team addTeam(Team team, int userId);
	List<Team> getMyTeams(int userId);
}
