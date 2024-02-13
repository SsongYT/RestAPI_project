package com.rmsoft.app.etc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	 @Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		 
		 httpSecurity.csrf((csrf) -> csrf.disable())
		 .formLogin((formLogin) -> formLogin.loginPage("/login").defaultSuccessUrl("/").permitAll());

		 
		 return httpSecurity.build();
	 }

}
