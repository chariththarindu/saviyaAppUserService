package com.saviya.api.users.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto implements Serializable {

	private static final long serialVersionUID = -735568728716084501L;

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String userId;
	private String encryptedPassword;	

}
