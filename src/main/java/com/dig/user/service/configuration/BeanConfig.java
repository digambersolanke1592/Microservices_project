package com.dig.user.service.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BeanConfig {
   
	@Bean
	@LoadBalanced   //for mapping localhost to given name like RATING-Services,HOTEL-Services
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}
