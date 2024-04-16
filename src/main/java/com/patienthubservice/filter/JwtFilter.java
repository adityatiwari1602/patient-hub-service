package com.patienthubservice.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.patienthubservice.responses.ErrorMessage;
import com.patienthubservice.service.IJwtUtil;

import io.jsonwebtoken.JwtException;


@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private IJwtUtil jwtUtil;
	
	@Autowired 
	private UserDetailsService userService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		String username = null;
		String jwt = null;
		
		try {
			
		
		if(authHeader!=null && authHeader.startsWith("Bearer "))
		{
			jwt = authHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null )
		{
			UserDetails userDetails = this.userService.loadUserByUsername(username);
			if(jwtUtil.validateToken(jwt, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken 
				= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		}
		catch(JwtException e) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(new ErrorMessage("Authorization Error", e.getMessage()));
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);			
			return;
		}
		filterChain.doFilter(request, response);
	}

}
