package com.xfashion.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResetPasswordDTO implements IsSerializable {

	private String key;
	
	private String username;
	
	private Date creationTimestamp;

	public ResetPasswordDTO() {

	}
	
	public ResetPasswordDTO(String username) {
		setUsername(username);
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

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

}
