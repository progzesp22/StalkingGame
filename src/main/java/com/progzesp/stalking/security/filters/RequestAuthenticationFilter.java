package com.progzesp.stalking.security.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.progzesp.stalking.utils.JwtTokenUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class RequestAuthenticationFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		if(request.getServletPath().equals("/user/login")) {
			filterChain.doFilter(request, response);
		}
		else {
			String authorizationHeader = request.getHeader("Authorization");
			String authPrefix = "Bearer ";
			if(authorizationHeader != null && authorizationHeader.startsWith(authPrefix)) {
				try {
					String token = authorizationHeader.substring(authPrefix.length());
					DecodedJWT decodedJWT = JwtTokenUtils.decodedJWT(token);
					String username = decodedJWT.getSubject();
					UsernamePasswordAuthenticationToken authenticationToken =
							new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				} catch (Exception e) {
					response.setHeader("error", e.getMessage());
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					Map<String, String> error = new HashMap<>();
					error.put("error_message", e.getMessage());
					response.setContentType(APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
			}
			else {
				filterChain.doFilter(request, response);
			}
		}
	}
}
