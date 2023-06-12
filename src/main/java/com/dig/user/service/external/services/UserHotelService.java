package com.dig.user.service.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dig.user.service.entities.Hotel;

@FeignClient(name = "HOTEL-SERVICES")
public interface UserHotelService {

	//this feignclient is total replacement of RestTemplate with small code.
	
	@GetMapping("/hotels/{hotelId}")
	Hotel getHotel(@PathVariable String hotelId);
	
}
