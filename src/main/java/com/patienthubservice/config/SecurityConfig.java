package com.patienthubservice.config;

import com.patienthubservice.filter.JwtFilter;
import com.patienthubservice.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	@Autowired
	private LoginUserService loginUserService;

	@Autowired
	private JwtFilter jwtFilter;

			public InMemoryUserDetailsManager userDetailsService() {
				UserDetails user = User.withDefaultPasswordEncoder()
						.username("user")
						.password("password")
						.roles("USER" )
						.build();
				return new InMemoryUserDetailsManager(user);
			}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.requestMatchers("/api/public/**").permitAll()
				.requestMatchers("/api/patient/**").hasRole("PATIENT")
				.requestMatchers("/api/center/**").hasRole("CENTER")
				.anyRequest().authenticated().and().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.cors();
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return new AuthenticationManager() {
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				return null;
			}
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
//		  return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}

}
