package com.ford.crudops.students;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {


	@Configuration
	public static class HttpSecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers("/api/**").permitAll()
				.antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/api-docs").permitAll()
				.requestMatchers(EndpointRequest.to("info", "health")).permitAll() // actuator
				.anyRequest().authenticated()
				.and()
			.httpBasic();
			//Added to allow localhost to permit connection to H2 - as spring-boot-starter-security
			//was added as a dependency.
			http.headers().frameOptions().disable();
		}
	}

}
