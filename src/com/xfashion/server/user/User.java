package com.xfashion.server.user;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.xfashion.shared.UserDTO;

@PersistenceCapable
public class User {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
    
    @Persistent
    private String username;
	
	@Persistent
	private String password;
	
	@Persistent
	private String description;
	
	@Persistent
	private String email;
	
	@Persistent
	private Boolean enabled;

	public User() {
		
	}
	
	public User(UserDTO dto) {
		updateFromDTO(dto);
	}
	
	public void updateFromDTO(UserDTO dto) {
		setUsername(dto.getUsername());
		setDescription(dto.getDescription());
		setEmail(dto.getEmail());
		setEnabled(dto.getEnabled());
	}
	
	public UserDTO createDTO() {
		UserDTO dto = new UserDTO();
		dto.setId(getId());
		dto.setUsername(getUsername());
		dto.setDescription(getDescription());
		dto.setEmail(getEmail());
		dto.setEnabled(getEnabled());
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void encodeAndSetPassword(String password) {
		String hash = MD5.getMD5Hash(password); 
		setPassword(hash);
	}
	
	public boolean validatePassword(String pwd) {
		if (pwd == null) {
			return false;
		}
		String hash = MD5.getMD5Hash(pwd);
		if(!hash.equals(getPassword())) {
			return false;
		}
		return true;
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
		if (enabled == null) {
			return true;
		}
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
