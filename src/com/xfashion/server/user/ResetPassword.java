package com.xfashion.server.user;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.xfashion.shared.ResetPasswordDTO;

@PersistenceCapable
public class ResetPassword {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
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
	
	public ResetPasswordDTO createDTO() {
		ResetPasswordDTO dto = new ResetPasswordDTO();
		dto.setUsername(getUsername());
		dto.setCreationTimestamp(getCreationTimestamp());
		dto.setId(getId());
		return dto;
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
