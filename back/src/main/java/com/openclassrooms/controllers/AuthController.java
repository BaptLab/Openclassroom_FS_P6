package com.openclassrooms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.exceptions.BadRequest;
import com.openclassrooms.exceptions.NotFound;
import com.openclassrooms.models.User;
import com.openclassrooms.payload.request.LoginRequest;
import com.openclassrooms.payload.request.RegisterRequest;
import com.openclassrooms.payload.response.LoginResponse;
import com.openclassrooms.payload.response.MessageResponse;
import com.openclassrooms.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
		User user = userService.findByEmail(loginRequest.getEmail());
		if (user != null) {
			LoginResponse loginResponse = userService.loginUser(loginRequest, user);
			return ResponseEntity.ok(loginResponse);
		} else {
			throw new NotFound();
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
		if (userService.findByEmail(registerRequest.getEmail()) != null) {
			throw new BadRequest();
		} else {
			User user = userService.registerRequestToUser(registerRequest);
			userService.save(user);
			return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		}
	}
	
}
