package com.test.kopnuspos.configuration;

import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	
	@Value("${jwt.token.expiration}")
    public long TOKEN_EXPIRATION;

    @Value("${jwt.signing.key}")
    public String SIGNING_KEY;
    
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public String generateToken(Authentication authentication) {
    	
    	String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

		return Jwts.builder()
				.setHeaderParam("typ","JWT")
				.claim("roles", authorities)
				.setSubject(authentication.getName())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION * 1000))
				.signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
				.compact();
	}


	public String getUserNameFromJwt(String token) {
		return Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException err) {
			logger.error("Invalid Jwt signature: {}", err.getMessage());
		} catch (MalformedJwtException err) {
			logger.error("Invalid Jwt token: {}", err.getMessage());
		} catch (ExpiredJwtException err) {
			logger.error("JWT token is expired: {}", err.getMessage());
		} catch (UnsupportedJwtException err) {
			logger.error("JWT token is unssuported: {}", err.getMessage());
		} catch (IllegalArgumentException err) {
			logger.error("JWT claims string is empty: {}", err.getMessage());
		}
		return false;
	}
}
