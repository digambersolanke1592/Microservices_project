package com.dig.user.service.controllers;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dig.user.service.Services.UserService;
import com.dig.user.service.Services.UserServiceImpl;
import com.dig.user.service.entities.User;

import ch.qos.logback.classic.Logger;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(UserController.class);

	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		
	User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	int retryCount = 1;
	
	@PreAuthorize(("hasAuthority('SCOPE_internal')) || hasAuthority('Admin')"))
	@GetMapping("/{userId}")
	//@CircuitBreaker(name = "hotelRatingBreaking",fallbackMethod = "ratingHotelFallBack")
	//@Retry(name = "hotelRatingService",fallbackMethod = "ratingHotelFallBack")
	@RateLimiter(name = "hotelRatingServiceLimiter",fallbackMethod = "ratingHotelFallBack")
	public ResponseEntity<User> getSingleuser(@PathVariable String userId){
     log.info("get single user handler : usercontroller");
     log.info("retyCount is : {}",retryCount);
     retryCount++;
	 User user = userService.getUserById(userId);
		return ResponseEntity.ok(user);
	}
	
	//creating fallback method for circuitbreaker
	
	public ResponseEntity<User> ratingHotelFallBack(String userId,Exception ex){
		log.info("Fallback is executed becausethe service is down : ",ex.getMessage() );
	User user =	User.builder()
			.email("dummyuse@gmail.com")
			.name("dummyuser")
			.about("the user created is dummy due to some services are down")
			.userId("123456")
			.build();
		    
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		
		List<User> allUser = userService.getAllUser();
		return ResponseEntity.ok(allUser);
		
	}
}
