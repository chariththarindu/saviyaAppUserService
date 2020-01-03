package com.saviya.api.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saviya.api.users.enitity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	UserEntity findByEmail (String email);

}
