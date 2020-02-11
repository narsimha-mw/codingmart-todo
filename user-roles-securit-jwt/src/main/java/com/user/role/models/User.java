package com.user.role.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(	name = "user",
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String username;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	@NotBlank
	private  String address;

	@NotBlank
	private  String city;

	@Column(name="contact_no")
	private  Long mobileNumber;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "users_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User(String username, String email, String password, String address, String city, Long mobileNumber) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.address=address;
		this.city=city;
		this.mobileNumber=mobileNumber;
	}
}