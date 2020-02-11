package com.travel.agent.role.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {

	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
