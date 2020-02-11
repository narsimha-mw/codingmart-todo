package com.travel.agent.role.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "agent",
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String username;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Size(min=4, max = 8)
	private String password;

	@NotBlank
	private  String address;

	@NotBlank
	private  String city;

	@NotBlank
	@Column(name="contact_no")
	private  Long mobileNumber;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "agent_roles",
				joinColumns = @JoinColumn(name = "agent_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public Agent(String username, String email, String password, String address, String city, Long mobileNumber) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.address=address;
		this.city=city;
		this.mobileNumber=mobileNumber;
	}
}
