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

import com.saviya.api.users.client.BusinessProductServiceClient;
import com.saviya.api.users.dto.UserDto;
import com.saviya.api.users.enitity.UserEntity;
import com.saviya.api.users.repository.UserRepository;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	BCryptPasswordEncoder brcyptPasswordEncorder;
	BusinessProductServiceClient businessProductServiceClient;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder brcyptPasswordEncorder,
			BusinessProductServiceClient businessProductServiceClient) {

		this.userRepository = userRepository;
		this.brcyptPasswordEncorder = brcyptPasswordEncorder;
		this.businessProductServiceClient = businessProductServiceClient;
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

	@Override
	public String productServiceStatusCheck() {

		String response = null;

		log.info("Before calling business product service");
		response = businessProductServiceClient.getBusinessProduct();
		log.info("after calling business product service");
		//log.error(e.getMessage());

		return response;
	}

}
