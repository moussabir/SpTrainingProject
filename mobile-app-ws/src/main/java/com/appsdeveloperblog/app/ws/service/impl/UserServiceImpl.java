package com.appsdeveloperblog.app.ws.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.UserRepository;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public UserDto createUser(UserDto user) {
			
		
			if (userRepository.findByEmail(user.getEmail())!= null) throw new RuntimeException ("Record already exists");
			UserEntity userEntity = new UserEntity();
			BeanUtils.copyProperties(user, userEntity);
			
			String publicUserId = utils.generateRandomString(30);
			userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			userEntity.setUserId(publicUserId);
			
			UserEntity  storedUserDetails =  userRepository.save(userEntity);
				
			UserDto returnValues = new UserDto();
			BeanUtils.copyProperties(storedUserDetails, returnValues);
			
			return returnValues;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
