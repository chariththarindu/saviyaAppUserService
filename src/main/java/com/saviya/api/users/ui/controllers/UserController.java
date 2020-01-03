package com.saviya.api.users.ui.controllers;

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

import com.saviya.api.users.dto.UserDto;
import com.saviya.api.users.service.UserService;
import com.saviya.api.users.ui.models.CreateUserRequestModel;
import com.saviya.api.users.ui.models.CreateUserResponseModel;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private Environment environemnt;

	@Autowired
	UserService userService;

	@GetMapping("/status/check")
	public String getStatus() {

		return "working on port :- " + environemnt.getProperty("local.server.port") + " token secret :->" + environemnt.getProperty("token.secret");
	}

	@PostMapping
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetail) {

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = mapper.map(userDetail, UserDto.class);
		UserDto careatedUser = userService.createUser(userDto);
		
		CreateUserResponseModel response = mapper.map(careatedUser, CreateUserResponseModel.class);
		return  ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
