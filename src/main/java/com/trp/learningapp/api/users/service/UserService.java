package com.trp.learningapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.trp.learningapp.api.users.shared.UserDto;

public interface UserService extends UserDetailsService{
	
	UserDto createUser (UserDto userduserDetails);
	
	UserDto getUserDetailsByEmail(String email);
	
	UserDto getUserByUserId(String userId);

}
