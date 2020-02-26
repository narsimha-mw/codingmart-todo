package com.user.role.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {

	private String token;
	private String token_type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private  String address;
	private Long mobileNumber;
	private String city;
	private List<String> roles;

    public JwtResponse(String jwt, Long id, String username, String email, String address, Long mobileNumber, String city, List<String> roles) {
		this.token = jwt;
		this.id = id;
		this.username = username;
		this.email = email;
		this.address=address;
		this.mobileNumber=mobileNumber;
		this.city=city;
		this.roles = roles;


	}
}
