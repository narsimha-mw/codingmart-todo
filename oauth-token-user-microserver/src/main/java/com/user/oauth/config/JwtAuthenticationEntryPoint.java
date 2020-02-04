package com.user.oauth.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Component
public class JwtAuthenticationEntryPoint implements Serializable, AuthenticationEntryPoint {

	private static final long serialVersionUID = -7858869558953243875L;


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {
		System.err.println("JwtAuthenticationEntryPoint commence Execution is calles");
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");		
	}
}
