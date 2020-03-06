package com.user.role.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.user.role.models.travel.TravelsAgent;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
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
//@Data
//@AllArgsConstructor
@DynamicUpdate
//@ToString
//@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String username;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@JsonIgnore
	private String password;

	@NotBlank
	private  String address;

	@NotBlank
	private  String city;

	@Column(name="contact_no")
	private  Long mobileNumber;

	@ManyToMany(targetEntity =  Role.class, fetch = FetchType.LAZY)
	@JoinTable(	name = "users_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY,
			orphanRemoval = true)
	@JsonIgnore
	private Set<TravelsAgent> agents;

	public User(){}
	public User(String username, String email, String password, String address, String city, Long mobileNumber) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.address=address;
		this.city=city;
		this.mobileNumber=mobileNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<TravelsAgent> getAgents() {
		return agents;
	}

	public void setAgents(Set<TravelsAgent> agents) {
		this.agents = agents;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", address='" + address + '\'' +
				", city='" + city + '\'' +
				", mobileNumber=" + mobileNumber +
				", roles=" + roles +
				", agents=" + agents +
				'}';
	}
}
