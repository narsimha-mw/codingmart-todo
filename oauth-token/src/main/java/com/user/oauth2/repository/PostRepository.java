package com.user.oauth2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.oauth2.model.Post;
import com.user.oauth2.model.UserDao;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	Post findByid(Long userId);
	List<Post> findByuser(UserDao user);
}
