package com.progzesp.stalking.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {
	@Override
	public void configure(HttpSecurity http) {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		LoginAuthenticationFilter filter = new LoginAuthenticationFilter(authenticationManager);
		filter.setFilterProcessesUrl("/user/login");
		http.addFilter(filter);
		http.addFilterBefore(new RequestAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}