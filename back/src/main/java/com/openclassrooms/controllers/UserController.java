package com.openclassrooms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.DTO.UserDTO;
import com.openclassrooms.exceptions.BadRequest;
import com.openclassrooms.models.User;
import com.openclassrooms.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") String id) {
	    try {
	        User user = userService.findById(Long.valueOf(id));
	        if (user == null) {
	            return ResponseEntity.notFound().build();
	        }
	        return ResponseEntity.ok().body(user);
	    } catch (NumberFormatException e) {
	        return ResponseEntity.badRequest().body(new BadRequest());
	    }
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody UserDTO userDto) {
	    try {
	        User user = userService.findById(Long.valueOf(id));
	        if (user == null) {
	            return ResponseEntity.notFound().build();
	        }
	        User updatedUser = userService.updateUser(user, userDto);
	        return ResponseEntity.ok().body(updatedUser);
	    } catch (NumberFormatException e) {
	        return ResponseEntity.badRequest().body(new BadRequest());
	    }
	}


}
