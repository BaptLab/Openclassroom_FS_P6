package com.openclassrooms.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.exceptions.BadRequest;
import com.openclassrooms.models.User;
import com.openclassrooms.payload.request.RegisterRequest;
import com.openclassrooms.payload.response.MessageResponse;
import com.openclassrooms.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
		if(userService.findByEmail(registerRequest.getEmail()) != null) {
	        throw new BadRequest();
		}
		else {
			User user = userService.registerRequestToUser(registerRequest);		
			userService.save(user);
	        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		}
	}
}

