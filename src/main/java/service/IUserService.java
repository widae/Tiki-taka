package service;

import java.util.Map;

import entity.User;

public interface IUserService{
	User addOrUpdateUser(User user);
	User updateUser(User user);
	User getUserById(Integer userId);
	void deleteUser(int userId);
	User getUserByToken(String token, Map<String, String> params);
	String getJWT(User user);
}
