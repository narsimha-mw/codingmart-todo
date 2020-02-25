package com.user.role.controllers;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.user.role.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.user.role.models.ERole;
import com.user.role.models.Role;
import com.user.role.models.User;
import com.user.role.payload.request.LoginRequestDTO;
import com.user.role.payload.request.SignupRequestDTO;
import com.user.role.payload.response.JwtResponse;
import com.user.role.payload.response.MessageResponse;
import com.user.role.repository.RoleRepository;
import com.user.role.security.jwt.JwtUtils;
import com.user.role.security.services.UserDetailsImpl;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserAuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/auth/user/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {


		//accepting client user username & pwd
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// response jwt string
		String jwt = jwtUtils.generateJwtToken(authentication);
		// get all user information in db
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		// calling roles pojo and specific user roles are getting
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		// in user JSON response are show in browser
		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(),
												 userDetails.getAddress(),
												 userDetails.getMobileNumber(),
												 userDetails.getCity(),
												 roles));
	}

	@PostMapping("/auth/user/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequestDTO) {
		if (userService.existsByUsername(signUpRequestDTO.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userService.existsByEmail(signUpRequestDTO.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in agent!"));
		}

		// Create new user's account
		User user = new User(signUpRequestDTO.getUsername(),
							 signUpRequestDTO.getEmail(),
							 encoder.encode(signUpRequestDTO.getPassword()),
				             signUpRequestDTO.getAddress(),
							 signUpRequestDTO.getCity(),
							 signUpRequestDTO.getMobileNumber());

		Set<String> strRoles = signUpRequestDTO.getRoles();
		Set<Role> roles = new HashSet<>();
		userActionsCases(strRoles, roles);

		user.setRoles(roles);
		userService.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	private void userActionsCases(Set<String> strRoles, Set<Role> roles) {
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "user":
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);

					break;
				default:
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);
				}
			});
		}
	}
}
