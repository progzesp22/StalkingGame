package com.progzesp.stalking.security;

import com.progzesp.stalking.security.filters.LoginAuthenticationFilter;
import com.progzesp.stalking.security.filters.RequestAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class UserSessionDsl extends AbstractHttpConfigurer<UserSessionDsl, HttpSecurity> {
	@Override
	public void configure(HttpSecurity http) {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

		LoginAuthenticationFilter loginFilter = new LoginAuthenticationFilter(authenticationManager);
		loginFilter.setFilterProcessesUrl("/user/login");
		http.addFilter(loginFilter);

		http.addFilterBefore(new RequestAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}