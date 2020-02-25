package com.user.oauth2.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.oauth2.exception.ResourceNotFoundException;
import com.user.oauth2.model.Comment;
import com.user.oauth2.model.Post;
import com.user.oauth2.repository.CommentRepository;
import com.user.oauth2.repository.PostRepository;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostController postController;
    @Autowired
    private PostRepository postRepository;

    @GetMapping(value = "/",produces = "application/json")
    public List<Comment> getAllCommentsByPostId(@PathVariable (value = "postId") Long postId) {
    	
    	List<Post> list= (List) postController.getUserName();
        return commentRepository.findByPostId(postId);
    }

    @PostMapping(value = "/add",produces = "application/json")
    public Comment createComment(@PathVariable (value = "postId") Long postId,
                                 @Valid @RequestBody Comment comment) {
    	 //Post findPost=postRepository.findByid(postId);
    	 //UserDao userdoa= postController.getUserName(); 	     	 
    	 List<Post> list= postRepository.findByuser(postController.getUserName());
    		ArrayList<Post> items = new ArrayList<Post>();
    				items.addAll(list);
    				 boolean valid=items.contains(postId);
    	  for(Post title : items){
    		if(title.getId()==postId) {
    			  return postRepository.findById(postId).map(posts -> {           
    		        	comment.setPost(posts);
    		            return commentRepository.save(comment);
    		        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    		}
    	  }
		 return comment;
      }

    @PutMapping(value = "/{commentId}",produces = "application/json")
    public Comment updateComment(@PathVariable (value = "postId") Long postId,
                                 @PathVariable (value = "commentId") Long commentId,
                                 @Valid @RequestBody Comment commentRequest) {
        if(!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("PostId " + postId + " not found");
        }

        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
    }

    @DeleteMapping(value = "/{commentId}",produces = "application/json")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "postId") Long postId,
                              @PathVariable (value = "commentId") Long commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and postId " + postId));
    }
}
