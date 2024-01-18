package com.leron.discoveryserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${eureka.username}")
	private String username;

	@Value("${eureka.password}")
	private String password;

	@Bean
	public UserDetailsService userDetailsService() {
		//use password encoder
		return new InMemoryUserDetailsManager(
				User.withDefaultPasswordEncoder()
						.username(username)
						.password(password)
						.roles("USER")
						.build()
		);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// ...
		http.csrf().disable()
				.authorizeRequests().anyRequest()
				.authenticated()
				.and()
				.httpBasic();
		return http.build();
	}



}
