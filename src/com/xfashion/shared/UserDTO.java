package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDTO implements IsSerializable {
	
	private String username;
	
	private String password;
	
	private String description;
	
	private String email;
	
	private Boolean enabled = true;

	public UserDTO() {
		
	}
	
	public UserDTO(String username, String description, String email) {
		setUsername(username);
		setDescription(description);
		setEmail(email);
	}
	
	public UserDTO(String username, String password, String description, String email) {
		setUsername(username);
		setPassword(password);
		setDescription(description);
		setEmail(email);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
}
