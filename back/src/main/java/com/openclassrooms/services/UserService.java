package com.openclassrooms.services;

import java.util.List;

import com.openclassrooms.models.User;
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
	
	public User update(Long id, User updatingUser ) {
		User existingUser = this.findById(id);
		if(existingUser != null) {
			existingUser.setEmail(updatingUser.getEmail());
			existingUser.setUsername(updatingUser.getUsername());
		}
		return existingUser;
	}

}
