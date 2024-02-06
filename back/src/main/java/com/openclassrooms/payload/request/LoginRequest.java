package com.openclassrooms.payload.request;

public class LoginRequest {

	private String emailOrUsername;
	
	private String password;

	public String getEmailOrUsername() {
		return emailOrUsername;
	}

	public void setEmailOrUsername(String email) {
		this.emailOrUsername = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
