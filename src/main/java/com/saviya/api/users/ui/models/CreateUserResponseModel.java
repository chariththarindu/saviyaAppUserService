package com.saviya.api.users.ui.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CreateUserResponseModel {

	private String firstName;
	private String lastName;
	private String email;
	private String userId;

}
