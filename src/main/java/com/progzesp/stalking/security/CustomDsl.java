package com.progzesp.stalking.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {
	@Override
	public void configure(HttpSecurity http) {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authenticationManager);
		filter.setFilterProcessesUrl("/user/login");
		http.addFilter(filter);
	}

	public static CustomDsl customDsl() {
		return new CustomDsl();
	}
}