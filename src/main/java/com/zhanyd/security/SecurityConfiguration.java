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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 使用自定义身份验证组件
        auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService,bCryptPasswordEncoder));
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http.cors().and().csrf().disable().authorizeRequests()  
         .antMatchers(HttpMethod.POST, "/api/user/signup").permitAll()
         .anyRequest().authenticated()  
         .and()  
         .addFilter(new JWTLoginFilter(authenticationManager()))  
         .addFilter(new JWTAuthenticationFilter(authenticationManager()));  
	}
}
