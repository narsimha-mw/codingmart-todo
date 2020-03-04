package com.user.role.controllers;

import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import com.user.role.repository.UserRepository;
import com.user.role.security.JwtUtils;
import com.user.role.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserAuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtils;

	public UserAuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
							  RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}

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
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		// in user JSON response are show in browser
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), userDetails.getAddress(), userDetails.getMobileNumber(),
				userDetails.getCity(), roles));
	}

	@PostMapping("/auth/user/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequestDTO) {

		if (userRepository.existsByUsername(signUpRequestDTO.getUsername()))
			return getResponseMessage("Error: Username is already in agent!");

		if (userRepository.existsByEmail(signUpRequestDTO.getEmail()))
			return getResponseMessage("Error: Email is already in agent!");

		// Create new user's account
		User user = new User(signUpRequestDTO.getUsername(),
				signUpRequestDTO.getEmail(),
				encoder.encode(signUpRequestDTO.getPassword()),
				signUpRequestDTO.getAddress(),
				signUpRequestDTO.getCity(),
				signUpRequestDTO.getMobileNumber()
		);
		userActionsCases(signUpRequestDTO.getRoles(), user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity getByUserId(@PathVariable(value = "userId") Long userId) {
		return new ResponseEntity(userRepository.findById(userId), HttpStatus.OK);
	}

	@PutMapping("/user/update/{userId}")
	public ResponseEntity updateUser(@PathVariable(value = "userId") Long userId,
									 @RequestBody SignupRequestDTO users) {
		userRepository.findById(userId).map(user -> {
			user.setUsername(users.getUsername());
			user.setEmail(users.getEmail());
			user.setMobileNumber(user.getMobileNumber());
			user.setAddress(users.getAddress());
			if(users.getRoles()==null){
				userRepository.save(user);
			}else {
				userActionsCases(users.getRoles(), user);
			}
			return ResponseEntity.ok(new MessageResponse("User Updated in successfully!"));
		});
		return ResponseEntity.ok(new MessageResponse("User Updated successfully!"));
	}
// response message display
	private ResponseEntity<MessageResponse> getResponseMessage(String message) {
		return ResponseEntity
				.badRequest()
				.body(new MessageResponse(message));
	}
	// dynamically user role added into set Role in User entity
	private void userActionsCases(Set<String> strRoles, User user) {
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			for (String role : strRoles) {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Specific user role is not found."));
						roles.add(adminRole);
						break;
					case "user":
						System.err.print(ERole.ROLE_USER);
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Specific user role is not found."));
						roles.add(userRole);
						break;
					default:
						Role modRole = roleRepository.findByName(ERole.ROLE_MODERATE)
								.orElseThrow(() -> new RuntimeException("Error: Specific user role is not found."));
						roles.add(modRole);
				}
			}
		}
		user.setRoles(roles);
		userRepository.save(user);
	}
}