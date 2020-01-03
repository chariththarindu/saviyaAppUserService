package com.saviya.api.users.ui.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequestModel {
	
	@NotNull(message = "First Name can not be null")
	@Size(min = 2 , message = "First name should contain more than two chacters" )
	private String firstName;
	
	@NotNull(message = "Last Name can not be null")
	@Size(min = 2 , message = "Last name should contain more than two chacters" )
	private String lastName;
	
	@NotNull(message = "Email can not be null")
	@Email(message = "Should be a valid email address")
	private String email;
	
	@NotNull(message = "Password can not be null")
	@Size(min = 2 , max = 16 , message = "Name should contain more than two chacters and less than 16 characters" )
	private String password;
		
}
