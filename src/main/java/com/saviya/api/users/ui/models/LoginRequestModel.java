package com.saviya.api.users.ui.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class LoginRequestModel {

	private String email;
	
	private String password;
	
}
