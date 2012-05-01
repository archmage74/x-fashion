package com.xfashion.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResetPasswordDTO implements IsSerializable {

	private Long id;
	
	private String username;
	
	private Date creationTimestamp;

	public ResetPasswordDTO() {

	}
	
	public ResetPasswordDTO(String username) {
		setUsername(username);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
