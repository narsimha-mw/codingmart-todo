package com.user.role;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@EnableResourceServer
@RestController
public class AgentRoleApplication {
	public static void main(String[] args) {
		SpringApplication.run(AgentRoleApplication.class, args);
		}

	@RequestMapping("/validateUser")
	public Principal user(Principal user) {
		return user;
	}
}
