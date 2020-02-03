package com.user.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.oauth.model.UserDao;



@Repository
public interface UserRepository  extends JpaRepository<UserDao, Long>{
	
	UserDao findByusername(String username);
}
