package com.leron.discoveryserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Value("${eureka.username}")
	private String username;

	@Value("${eureka.password}")
	private String password;

	@Bean
		//use password encoder
		public InMemoryUserDetailsManager userDetailsService() {
			UserDetails user = User.withDefaultPasswordEncoder()
					.username(username)
					.password(passwordEncoder().encode(password))
					.roles("USER")
					.build();
			return new InMemoryUserDetailsManager(user);
		}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().ignoringRequestMatchers("/eureka/**");
		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
