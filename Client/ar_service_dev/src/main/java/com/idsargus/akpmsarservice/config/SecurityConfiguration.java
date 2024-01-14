package com.idsargus.akpmsarservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


	//for 401 response if token expired.
//	@Autowired
//	JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests().antMatchers("v1/arapi").authenticated().anyRequest()
//				.permitAll().and()
//				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

		http.authorizeRequests().antMatchers("v1/arapi").permitAll().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.NEVER);


	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.GET, "/forget/password/**");
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
}
