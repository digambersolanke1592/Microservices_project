package com.dig.user.service.Services;

import java.util.List;

import com.dig.user.service.entities.User;

public interface UserService {

	public User saveUser(User user);
	
	public List<User> getAllUser();
	
	public User getUserById(String userId);
}
