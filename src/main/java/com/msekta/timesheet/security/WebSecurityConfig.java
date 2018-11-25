package com.msekta.timesheet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationFilter authFilter;

	@Autowired
	private JwtExceptionHandlerFilter exceptionHandlerFilter;

	@Autowired
	private LoginDetailService dbUserDetailService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/login").permitAll()
			.antMatchers("/project/user").hasAnyAuthority("ADMIN", "MANAGER", "ACCOUNTANT", "WORKER")
			.antMatchers("/project/short/all").hasAuthority("ADMIN")
			.antMatchers("/project/all").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.POST, "/project").hasAuthority("ADMIN")
			.antMatchers(HttpMethod.PUT, "/project").hasAuthority("ADMIN")
			.antMatchers("/user/**").hasAnyAuthority("ADMIN", "ACCOUNTANT")
			.antMatchers("/worklog/timesheet").hasAnyAuthority("ADMIN", "MANAGER", "ACCOUNTANT", "WORKER")
			.antMatchers(HttpMethod.PUT, "/worklog").hasAnyAuthority("ADMIN", "MANAGER", "ACCOUNTANT", "WORKER")
			.antMatchers(HttpMethod.DELETE, "/worklog/**").hasAnyAuthority("ADMIN", "MANAGER", "ACCOUNTANT", "WORKER")
			.antMatchers("/worklog/**").hasAnyAuthority("MANAGER", "ADMIN")
			.antMatchers("/stats/**").hasAnyAuthority("ADMIN", "MANAGER", "ACCOUNTANT", "WORKER")
			.anyRequest().authenticated();

		http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(dbUserDetailService);
		return provider;
	}


}
