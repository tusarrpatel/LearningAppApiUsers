package com.trp.learningapp.api.users.ui.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trp.learningapp.api.users.service.UserService;
import com.trp.learningapp.api.users.shared.UserDto;
import com.trp.learningapp.api.users.ui.model.CreateUserRequestModel;
import com.trp.learningapp.api.users.ui.model.CreateUserResponseModel;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	Environment env;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/status/check")
	public String status() {
		return "User Controller is working on port "+env.getProperty("local.server.port")+", "+env.getProperty("token.secret");
	}
	
	@PostMapping
	public ResponseEntity<CreateUserResponseModel> createUser( @Valid @RequestBody CreateUserRequestModel userDetails) {
		
		 
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = mapper.map(userDetails, UserDto.class);
		UserDto createdUser = userService.createUser(userDto);
		CreateUserResponseModel returnValue = mapper.map(createdUser, CreateUserResponseModel.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	} 

}
