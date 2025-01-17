package com.openclassrooms.payload.response;

public class LoginResponse {

	private String token;
	private String username;
	private String email;
	private Long id;
	
	public LoginResponse(String token, String username, String email, Long id) {
		this.token = token;
		this.username = username;
		this.email = email;
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
}
