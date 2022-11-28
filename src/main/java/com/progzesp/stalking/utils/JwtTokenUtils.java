package com.progzesp.stalking.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public final class JwtTokenUtils {

	// TODO generate secretKey
	private static final String secretKey = "secret xd";

	public static String generateToken(String username, String issuer, Date expirationDate) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		String token = JWT.create()
				.withSubject(username)
				.withExpiresAt(expirationDate)
				.withIssuer(issuer)
				.sign(algorithm);
		return token;
	}

	public static DecodedJWT decodedJWT(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		JWTVerifier jwtVerifier = JWT.require(algorithm).build();
		return jwtVerifier.verify(token);
	}

}
