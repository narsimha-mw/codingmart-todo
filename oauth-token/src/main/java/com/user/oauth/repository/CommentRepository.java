package com.user.oauth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.oauth.model.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	List<Comment> findByPostId(Long postId);
    Optional<Comment> findByIdAndPostId(Long id, Long postId);

	

}
