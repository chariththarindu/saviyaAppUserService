package com.saviya.api.users.enitity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@Data
@Setter
@Getter
@NoArgsConstructor
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -5791327612949560115L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false , length = 50)
	private String firstName;
	
	@Column(nullable = false , length = 50)
	private String lastName;
	
	@Column(nullable = false , length = 120 , unique = true)
	private String email;
	
	@Column(nullable = false , unique = true)
	private String userId;
	
	@Column(nullable = false , unique = true)
	private String encryptedPassword;
	
	
}
