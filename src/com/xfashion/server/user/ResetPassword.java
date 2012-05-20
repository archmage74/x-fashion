package com.xfashion.server.user;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.ResetPasswordDTO;

@PersistenceCapable
public class ResetPassword {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String username;
	
	@Persistent
	private Date creationTimestamp;

	public ResetPassword() {
		setCreationTimestamp(new Date());
	}
	
	public ResetPassword(ResetPasswordDTO dto) {
		setCreationTimestamp(new Date());
		setUsername(dto.getUsername());
	}
	
	public Key getKey() {
		return key;
	}
	
	public String getKeyAsString() {
		return KeyFactory.keyToString(key);
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

	public ResetPasswordDTO createDTO() {
		ResetPasswordDTO dto = new ResetPasswordDTO();
		dto.setKey(getKeyAsString());
		dto.setUsername(getUsername());
		dto.setCreationTimestamp(getCreationTimestamp());
		return dto;
	}
	
}
