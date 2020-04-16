package com.trp.learningapp.api.users.security;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trp.learningapp.api.users.service.UserService;
import com.trp.learningapp.api.users.shared.UserDto;
import com.trp.learningapp.api.users.ui.model.LoginRequestModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private UserService userService;
	private Environment env;
	
	@Autowired
	public AuthenticationFilter(UserService userService,Environment env, AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
		this.userService = userService;
		this.env = env;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			LoginRequestModel loginRequestModel = new ObjectMapper().readValue(request.getInputStream(),
					LoginRequestModel.class);
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					loginRequestModel.getEmail(), loginRequestModel.getPassword(), new ArrayList<>()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String userName = ((User)authResult.getPrincipal()).getUsername();
		UserDto returnedUser = userService.getUserDetailsByEmail(userName);
		String token = Jwts.builder()
				.setSubject(returnedUser.getUserId())
				.setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(env.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("token_secret"))
				.compact();
		
		response.addHeader("token", token);
		response.addHeader("userId ", returnedUser.getUserId());
		
		
	//	super.successfulAuthentication(request, response, chain, authResult);
	}

}
