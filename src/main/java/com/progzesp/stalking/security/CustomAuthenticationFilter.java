package com.progzesp.stalking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final long expirationTime = (long) 1000 * 60 * 60 * 24;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

	private AuthenticationManager authenticationManager;

	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String username = request.getParameter("login");
		String password = request.getParameter("password");
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		User user = (User) authResult.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("secret");
		Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
		String token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(expirationDate)
				.withIssuer(request.getRequestURL().toString())
				.sign(algorithm);
		response.setContentType(APPLICATION_JSON_VALUE);
		Map<String, String> body = new HashMap<>();
		body.put("sessionToken", token);
		body.put("validUntil", dateFormat.format(expirationDate));
		new ObjectMapper().writeValue(response.getOutputStream(), body);
	}
}
