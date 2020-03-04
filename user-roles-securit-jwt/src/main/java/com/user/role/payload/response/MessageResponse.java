package com.user.role.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class MessageResponse {
	private String message;
	private HttpStatus status;
	public MessageResponse(String message) {
		this.message = message;
	}


	public MessageResponse(String message, HttpStatus status) {
		this.message = message;
		this.status=status;
	}
}
