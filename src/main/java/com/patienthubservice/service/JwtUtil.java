package com.patienthubservice.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.patienthubservice.dao.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil implements IJwtUtil {

	private final String SECRET_KEY = "BP8HoYR4hxBJAbhF9N0dlRgkc6y3TjyXZcgRaoSQNSjwGYAsbjmHPYrS2qqZjZJ";

	@Autowired
	private UserRepository userRepository;
	
    @Override
	public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
	public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
	public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String role = userRepository.findByUsername(userDetails.getUsername()).getRole();
        return createToken(claims, userDetails.getUsername(), role);
    }

    private String createToken(Map<String, Object> claims, String subject, String role) {

        return Jwts.builder().setClaims(claims).setId(role).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24 ))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    @Override
	public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
