package com.travel.agent.role.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.agent.role.models.ERole;
import com.travel.agent.role.models.Role;
import com.travel.agent.role.models.Agent;
import com.travel.agent.role.payload.request.LoginRequestDTO;
import com.travel.agent.role.payload.request.SignupRequestDTO;
import com.travel.agent.role.payload.response.JwtResponse;
import com.travel.agent.role.payload.response.MessageResponse;
import com.travel.agent.role.repository.RoleRepository;
import com.travel.agent.role.repository.AgentRepository;
import com.travel.agent.role.security.jwt.JwtUtils;
import com.travel.agent.role.security.services.AgentDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/agent")
public class AgentAuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AgentRepository agentRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		AgentDetailsImpl userDetails = (AgentDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequestDTO) {
		if (agentRepository.existsByUsername(signUpRequestDTO.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (agentRepository.existsByEmail(signUpRequestDTO.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in agent!"));
		}

		// Create new user's account
		Agent user = new Agent(signUpRequestDTO.getUsername(),
							 signUpRequestDTO.getEmail(),
							 encoder.encode(signUpRequestDTO.getPassword()),
				             signUpRequestDTO.getAddress(),
							 signUpRequestDTO.getCity(),
							 signUpRequestDTO.getMobileNumber());

		Set<String> strRoles = signUpRequestDTO.getRole();
		Set<Role> roles = new HashSet<>();

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
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		agentRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Agent registered successfully!"));
	}
}
