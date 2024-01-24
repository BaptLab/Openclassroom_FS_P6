package com.openclassrooms.services;

import java.util.List;

import com.openclassrooms.DTO.UserDTO;
import com.openclassrooms.exceptions.BadRequest;
import com.openclassrooms.models.User;
import com.openclassrooms.payload.request.LoginRequest;
import com.openclassrooms.payload.request.RegisterRequest;
import com.openclassrooms.payload.response.LoginResponse;
import com.openclassrooms.repository.UserRepository;
import com.openclassrooms.utils.JwtUtils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	static private JwtUtils jwtUtils;

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User findById(Long id) {
		return this.userRepository.findById(id).orElse(null);
	}

	public void delete(Long id) {
		this.userRepository.deleteById(id);
	}

	public User save(User user) {
		return this.userRepository.save(user);
	}

	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User registerRequestToUser(RegisterRequest registerRequest) {
		User user = new User();
		user.setEmail(registerRequest.getEmail());
		String encryptedPwd = JwtUtils.pwdEncoder(registerRequest.getPassword());
		user.setPassword(encryptedPwd);
		user.setUsername(registerRequest.getUsername());
		return user;
	}

	public User update(Long id, UserDTO updatingUser) {
		User existingUser = this.findById(id);
		if (existingUser != null) {
			existingUser.setEmail(updatingUser.getEmail());
			existingUser.setUsername(updatingUser.getUsername());
			existingUser.setId(updatingUser.getId());
		}
		return existingUser;
	}

	public UserDTO userToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setUsername(user.getUsername());
		return userDTO;
	}

	public LoginResponse loginUser(LoginRequest loginRequest, User user) {
		if (JwtUtils.isPwdMatching(loginRequest, user)) {
			Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
					user.getPassword());
			String token = JwtUtils.generateToken(authentication);
			LoginResponse loginResponse = new LoginResponse(token, user.getUsername(), user.getEmail(), user.getId());
			return loginResponse;
		} else {
			throw new BadRequest();
		}
	}

	public User updateUser(User user, UserDTO userDto) {
		user.setEmail(userDto.getEmail());
		user.setUsername(userDto.getUsername());
		user.setId(userDto.getId());
		return user;
	}

}
