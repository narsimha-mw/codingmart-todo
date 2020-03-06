package com.user.role.controllers;

import com.user.role.models.ERole;
import com.user.role.models.Role;
import com.user.role.models.User;
import com.user.role.payload.request.SignupRequestDTO;
import com.user.role.payload.response.MessageResponse;
import com.user.role.repository.RoleRepository;
import com.user.role.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public UserController( UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@GetMapping("/users/list")
	public ResponseEntity<List<User>> getAllUsers(){
		return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
	}
	@GetMapping("/user/{userId}")
	public ResponseEntity getByUserId(@PathVariable(value = "userId") Long userId) {
		if (userId!=null) {
			boolean response = userRepository.findById(userId).isPresent();
			System.err.print(response + "response: " + response);
			if (response) {
				return new ResponseEntity<>(userRepository.findById(userId), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("user id doesn't exists ", HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity("User is not passing", HttpStatus.BAD_REQUEST);
		}
	}
	@PutMapping("/user/update/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable(value = "userId") Long userId,
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
			return ResponseEntity.ok(new MessageResponse("User Updated in successfully!",HttpStatus.OK));
		});
		return ResponseEntity.ok(new MessageResponse("User Updated successfully!"));
	}
	@DeleteMapping("/user/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId){

		if(userId!=null){
			System.err.print("delete is calling");
			userRepository.deleteById(userId);
//			System.err.print(response);
			return new ResponseEntity<>("Successfully delted", HttpStatus.OK);
		}
		return  new ResponseEntity<>("User id is not flound", HttpStatus.BAD_REQUEST);
	}
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