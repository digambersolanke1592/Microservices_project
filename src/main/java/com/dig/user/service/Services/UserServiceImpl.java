package com.dig.user.service.Services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dig.user.service.entities.Hotel;
import com.dig.user.service.entities.Rating;
import com.dig.user.service.entities.User;
import com.dig.user.service.exceptions.ResourceNotFoundException;
import com.dig.user.service.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RestTemplate restTemplate;

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

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
		// get single user with ratings details
		User user = userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("user with given id is not found on server !! :" + userId));
		Rating[] getRating = restTemplate.getForObject("http://localhost:8083/ratings/user/" + userId, Rating[].class);

		log.info("{}", getRating);

		//her also getting hotel details
		
		List<Rating> ratings = Arrays.stream(getRating).toList();

		List<Rating> ratingList = ratings.stream().map(rating -> {
			
			String str = rating.getHotelId();
            log.info("this log gives hotelId  --"+rating.getHotelId());
			ResponseEntity<Hotel> hot = restTemplate.getForEntity("http://localhost:8082/hotels/" + rating.getHotelId(),Hotel.class);
			Hotel hotel1 = hot.getBody();
			System.out.println(hotel1);
			log.info("response status code:{}", hot.getStatusCode());
			rating.setHotel(hotel1);
			return rating;
			
		}).collect(Collectors.toList());
		user.setRating(ratingList);
		System.out.println(user);
		return user;
	}

	// user operations are here

}
