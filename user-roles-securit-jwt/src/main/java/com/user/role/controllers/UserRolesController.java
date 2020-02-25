package com.user.role.controllers;

import com.user.role.models.User;
import com.user.role.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/roles")
public class UserRolesController {


	@Autowired
	UserRepository userRepository;
	@GetMapping("/")
	public  List<User> getAllUserRoles() {
		return userRepository.findAll();
	}
	@GetMapping("/roles/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String agentUserAccess() {
		return "User role Content.";
	}

	@GetMapping("/roles/mode")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return " User role Moderator Board.";
	}

	@GetMapping("/roles/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "User role Admin Board.";
	}
}
