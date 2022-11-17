package com.progzesp.stalking.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.exceptionHandling().authenticationEntryPoint(
				(request, response, ex) -> response.sendError(
						HttpServletResponse.SC_UNAUTHORIZED,
						ex.getMessage()
				)
		);

		http.authorizeRequests()
				.antMatchers("/user/login").permitAll()
				.anyRequest().authenticated();

		http.apply(new CustomDsl());
		return http.build();
	}

}
