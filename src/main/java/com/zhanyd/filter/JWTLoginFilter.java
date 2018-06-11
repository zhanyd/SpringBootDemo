package com.zhanyd.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
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
	
	private AuthenticationManager authenticationManager;
	
	public JWTLoginFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	// 接收并解析用户凭证
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		 try {
	            Users user = new ObjectMapper().readValue(request.getInputStream(), Users.class);
	            return authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(
	                            user.getUsername(),
	                            user.getPassword(),
	                            new ArrayList<>())
	            );
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	}
	
	//用户成功登录后，这个方法会被调用，我们在这个方法里生成token
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        // builder the token
        String token = null;
        try {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            // 定义存放角色集合的对象
            List roleList = new ArrayList<>();
            for (GrantedAuthority grantedAuthority : authorities) {
                roleList.add(grantedAuthority.getAuthority());
            }
            token = Jwts.builder()
                    .setSubject(auth.getName() + "-" + roleList)
                    .setExpiration(new Date(System.currentTimeMillis() + 60 * 1000)) // 设置过期时间 60秒
                    .signWith(SignatureAlgorithm.HS512, "MyJwtSecret") //采用什么算法是可以自己选择的，不一定非要采用HS512
                    .compact();
            // 登录成功后，返回token到header里面
            response.addHeader("Authorization", "Bearer " + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
