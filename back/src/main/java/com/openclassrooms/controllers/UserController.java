package com.openclassrooms.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.exceptions.BadRequest;
import com.openclassrooms.models.User;
import com.openclassrooms.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") String id) {
	    try {
	        User user = userService.findById(Long.valueOf(id));
	        if (user == null) {
	            return ResponseEntity.notFound().build();
	        }
	        return ResponseEntity.ok().body(userService.userToDTO(user));
	    } catch (NumberFormatException e) {
	        return ResponseEntity.badRequest().body(new BadRequest());
	    }
	}


}
