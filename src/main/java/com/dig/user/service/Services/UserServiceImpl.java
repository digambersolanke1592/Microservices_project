package com.dig.user.service.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dig.user.service.entities.User;
import com.dig.user.service.exceptions.ResourceNotFoundException;
import com.dig.user.service.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User saveUser(User user) {
		// here we are generating unique userId in string at realtime
		
	  String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
	  return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User getUserById(String userId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with given id is not found on server !! :"+userId)) ;
	}

	//user operations are here
	

}
