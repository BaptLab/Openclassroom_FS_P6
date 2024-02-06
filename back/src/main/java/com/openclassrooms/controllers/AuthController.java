package com.openclassrooms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.models.User;
import com.openclassrooms.payload.request.LoginRequest;
import com.openclassrooms.payload.request.RegisterRequest;
import com.openclassrooms.payload.response.LoginResponse;
import com.openclassrooms.payload.response.MessageResponse;
import com.openclassrooms.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            User userBasedOnEmail = userService.findByEmail(loginRequest.getEmailOrUsername());
            if (userBasedOnEmail == null) {
                User userBasedOnUsername = userService.findByUsername(loginRequest.getEmailOrUsername());
                if (userBasedOnUsername == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                } else {
                    LoginResponse loginResponse = userService.loginUser(loginRequest, userBasedOnUsername);
                    return ResponseEntity.ok(loginResponse);
                }
            } else {
                LoginResponse loginResponse = userService.loginUser(loginRequest, userBasedOnEmail);
                return ResponseEntity.ok(loginResponse);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials format");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            if (!userService.isEmailValid(registerRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email format");
            }

            if (!userService.isPasswordValid(registerRequest.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password format");
            }

            if (!userService.isUsernameValid(registerRequest.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username format");
            }

            if (userService.findByEmail(registerRequest.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this email already exists");
            } else {
                User user = userService.registerRequestToUser(registerRequest);
                userService.save(user);
                return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid registration request format");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }
}
