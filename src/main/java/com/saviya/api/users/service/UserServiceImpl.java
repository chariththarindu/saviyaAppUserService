package com.saviya.api.users.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.saviya.api.users.dto.UserDto;
import com.saviya.api.users.enitity.UserEntity;
import com.saviya.api.users.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	BCryptPasswordEncoder brcyptPasswordEncorder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder brcyptPasswordEncorder) {

		this.userRepository = userRepository;
		this.brcyptPasswordEncorder = brcyptPasswordEncorder;
	}

	@Override
	public UserDto createUser(UserDto userDto) {

		userDto.setUserId(UUID.randomUUID().toString());
		userDto.setEncryptedPassword(brcyptPasswordEncorder.encode(userDto.getPassword()));
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = mapper.map(userDto, UserEntity.class);
		UserEntity savedEntity = userRepository.save(userEntity);

		return mapper.map(savedEntity, UserDto.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = userRepository.findByEmail(username);
		if (user == null)
			throw new UsernameNotFoundException(username);

		return new User(user.getEmail(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDto findUserByEmail(String email) {
		UserEntity user = userRepository.findByEmail(email);
		if (user == null)
			throw new UsernameNotFoundException(email);

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		return mapper.map(user, UserDto.class);
	}

}
