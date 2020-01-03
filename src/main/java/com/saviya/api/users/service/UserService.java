package com.saviya.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.saviya.api.users.dto.UserDto;

public interface UserService extends UserDetailsService{
	
	UserDto createUser(UserDto userDto);
	UserDto findUserByEmail(String email) ;
 
}
