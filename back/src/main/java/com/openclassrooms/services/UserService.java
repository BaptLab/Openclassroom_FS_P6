package com.openclassrooms.services;

import java.util.ArrayList;
import java.util.List;

import com.openclassrooms.DTO.UserDTO;
import com.openclassrooms.models.Theme;
import com.openclassrooms.models.User;
import com.openclassrooms.payload.request.RegisterRequest;
import com.openclassrooms.repository.UserRepository;

public class UserService {

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
	
	public List<User> findAll(){
		return this.userRepository.findAll();
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User registerRequestToUser(RegisterRequest registerRequest) {
		User user = new User();
		user.setEmail(registerRequest.getEmail());
		user.setPassword(registerRequest.getPassword());
		user.setUsername(registerRequest.getUsername());
		return user;
	}
	
	public UserDTO userToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail(user.getEmail());
		userDTO.setUsername(user.getUsername());
		List<Theme> themes = new ArrayList<>(user.getThemes());
	    userDTO.setThemes(themes);
		return userDTO;
	}
	
	public User update(Long id, User updatingUser ) {
		User existingUser = this.findById(id);
		if(existingUser != null) {
			existingUser.setEmail(updatingUser.getEmail());
			existingUser.setUsername(updatingUser.getUsername());
			existingUser.setThemes(updatingUser.getThemes());
		}
		return existingUser;
	}

}
