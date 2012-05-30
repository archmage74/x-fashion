package com.xfashion.shared;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDTO implements IsSerializable, Serializable {

	private static final long serialVersionUID = -5257634660252618442L;

	private String key;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private Boolean enabled = true;
	
	private ShopDTO shop;
	
	private UserCountry country;
	
	private UserRole role;

	public UserDTO() {
		shop = new ShopDTO();
	}
	
	public UserDTO(String username, String email) {
		setUsername(username);
		setEmail(email);
	}
	
	public UserDTO(String username, String password, String email, UserCountry country, UserRole role) {
		setUsername(username);
		setPassword(password);
		setEmail(email);
		setCountry(country);
		setRole(role);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public ShopDTO getShop() {
		return shop;
	}

	public void setShop(ShopDTO shop) {
		this.shop = shop;
	}

	public UserCountry getCountry() {
		return country;
	}

	public void setCountry(UserCountry country) {
		this.country = country;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

}
