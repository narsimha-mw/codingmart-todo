package com.user.oauth.api;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;


//@Entity
//@Table(name="users")
public class UserGlobalValues {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
    @Column(name="first_name")
	private String firstName;
    
    @Column(name="last_name")
    private String lastName;
    
    private String username;
    private String password;
    private String email;
    private List<?> role;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<?> getRole() {
		return role;
	}
	public void setRole(List<?> role) {
		this.role = role;
	}
	
	

}
