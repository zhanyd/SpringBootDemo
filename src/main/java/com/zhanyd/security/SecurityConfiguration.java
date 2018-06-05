package com.zhanyd.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import com.zhanyd.filter.JWTAuthenticationFilter;
import com.zhanyd.filter.JWTLoginFilter;

@Configurable
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

static final String SELF_CSRF_COOKIE_NAME = "_token";
	
	@Autowired
	protected DataSource dataSource;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jdbcUserDetailsManager()) ;
	}
	
	public UserDetailsManager jdbcUserDetailsManager() throws Exception {
		JdbcUserDetailsManager userMan = new JdbcUserDetailsManager();
		userMan.setDataSource( dataSource ) ;
		userMan.setRolePrefix("ROLE_");
		userMan.setUsersByUsernameQuery( "select username,password,enabled from users where username = ?" ); 
		userMan.setAuthoritiesByUsernameQuery("select username,authority from authorities where username = ?");
		return userMan;
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http.cors().and().csrf().disable().authorizeRequests()  
         .antMatchers(HttpMethod.POST, "/api/user/signup").permitAll()  
         .antMatchers(HttpMethod.POST, "/login").permitAll()
         .anyRequest().authenticated()  
         .and()  
         .addFilter(new JWTLoginFilter(authenticationManager()))  
         .addFilter(new JWTAuthenticationFilter(authenticationManager()));  
	}
}
