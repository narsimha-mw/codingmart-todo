package com.user.oauth2.model;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class UserDao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String username;
	@Column
	@JsonIgnore
	private String password;

	@OneToMany(fetch = FetchType.EAGER, 
			   mappedBy = "user", 
			   cascade = CascadeType.ALL)
	private Set<Post> post=new HashSet<Post>();

	public UserDao() {
	}

	public Set<Post> getPost() {
		return post;
	}

	public void setPost(Set<Post> post) {
		this.post = post;
	}

	public boolean equals(Object obj) {
		return post.equals(obj);
	}

	public int hashCode() {
		return post.hashCode();
	}

	public String toString() {
		return post.toString();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
