package com.dig.user.service.configuration.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

	@Autowired
	private OAuth2AuthorizedClientManager manager;     //it gives the token value
	
	
	
	@Override
	public void apply(RequestTemplate template) {
		// TODO Auto-generated method stub
		
	String token = manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build()).getAccessToken().getTokenValue();
		
		template.header("Authorization", "Bearer"+token);
	}

}
