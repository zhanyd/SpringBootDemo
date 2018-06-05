package com.zhanyd.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhanyd.biz.model.Users;
import com.zhanyd.common.ApiResult;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by Administrator on 2018/6/4 0004.
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter{
	
	public JWTLoginFilter(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager); 
	}
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			/*String authorization = request.getHeader("Authorization"); 
			if(authorization == null || authorization.equals("")){
				return super.attemptAuthentication(request, response)  ;  
			}
			Users user = new ObjectMapper().readValue(authorization , Users.class);*/
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			Users user = new ObjectMapper().readValue(request.getInputStream() , Users.class);
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), "", new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e); 
		}
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req; 
		if(request.getMethod().equalsIgnoreCase("POST")){
			super.doFilter(request, res, chain);
			return ;
		}
		chain.doFilter(request, res );
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) {
		String token = Jwts.builder()
				.setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS512, "MyJwtSecret").compact();
		response.addHeader("Authorization", token);
		try {
			 ApiResult result = new ApiResult();
			 ObjectMapper mapper = new ObjectMapper();
			response.getWriter().write( mapper.writeValueAsString(result.success(auth.getName()))); 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
