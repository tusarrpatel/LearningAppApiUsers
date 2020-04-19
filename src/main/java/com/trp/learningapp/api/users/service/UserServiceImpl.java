package com.trp.learningapp.api.users.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.trp.learningapp.api.users.data.AlbumsServiceClient;
import com.trp.learningapp.api.users.data.UserEntity;
import com.trp.learningapp.api.users.data.UserRepository;
import com.trp.learningapp.api.users.shared.UserDto;
import com.trp.learningapp.api.users.ui.model.AlbumResponseModel;
import com.trp.learningapp.api.users.ui.model.CreateUserRequestModel;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	AlbumsServiceClient albumsServiceClient;

	@Override
	public UserDto createUser(UserDto userDetails) {
		// TODO Auto-generated method stub
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(this.bCryptPasswordEncoder.encode(userDetails.getPassword()));
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = mapper.map(userDetails, UserEntity.class);
		// userEntity.setEncryptedPassword("test");
		userRepository.save(userEntity);
		UserDto returnValue = mapper.map(userEntity, UserDto.class);
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity userEntity = userRepository.findByEmail(username);
		if(userEntity == null) {
			throw new UsernameNotFoundException("user name do not exist in database");
		}
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),
				true,true,true,true,new ArrayList<>()); 			
			
	}
	
	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) {
			throw new UsernameNotFoundException("email  do not exist in database");
		}
		ModelMapper mapper = new ModelMapper();
		UserDto returnValue = mapper.map(userEntity, UserDto.class);
		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findUserByUserId(userId);
		if(userEntity == null) {
			throw new UsernameNotFoundException("email  do not exist in database");
		}
		ModelMapper mapper = new ModelMapper();
		UserDto returnValue = mapper.map(userEntity, UserDto.class);
		List<AlbumResponseModel> responseModel = albumsServiceClient.getAlbums(userId);
		returnValue.setAlbums(responseModel);
		return returnValue;
	}

}
