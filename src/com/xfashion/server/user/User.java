package com.xfashion.server.user;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.UserCountry;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.UserRole;

@PersistenceCapable
public class User {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
    
    @Persistent
    private String username;
	
	@Persistent
	private String password;
	
	@Persistent
	private String email;
	
	@Persistent
	private Boolean enabled;
	
	@Persistent
	private Shop shop;
	
	@Persistent
	private String country;
	
	@Persistent
	private String role;
	
	@Persistent
	private Date lastKeepAlive;
	
	public User(UserDTO dto) {
		shop = new Shop();
		updateFromDTO(dto);
	}
	
	public Key getKey() {
		return key;
	}
	
	public String getKeyAsString() {
		return KeyFactory.keyToString(key);
	}
	
	public void setKey(Key key) {
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

	public Shop getShop() {
		if (shop.getCountry() == null) {
			shop.setCountry(country);
		}
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public Date getLastKeepAlive() {
		return lastKeepAlive;
	}

	public void setLastKeepAlive(Date lastKeepAlive) {
		this.lastKeepAlive = lastKeepAlive;
	}

	public void updateFromDTO(UserDTO dto) {
		setUsername(dto.getUsername());
		setEmail(dto.getEmail());
		setEnabled(dto.getEnabled());
		setCountry(dto.getCountry().name());
		setRole(dto.getRole().name());
		if (dto.getShop() != null) {
			if (getShop() != null) {
				getShop().updateFromDTO(dto.getShop());
			} else {
				setShop(new Shop(dto.getShop()));
			}
		}
	}
	
	public UserDTO createDTO() {
		UserDTO dto = new UserDTO();
		dto.setKey(getKeyAsString());
		dto.setUsername(getUsername());
		dto.setEmail(getEmail());
		dto.setEnabled(getEnabled());
		dto.setShop(getShop().createDTO());
		dto.setLastKeepAlive(getLastKeepAlive());
		if (getCountry() == null) {
			dto.setCountry(UserCountry.AT);
		} else {
			dto.setCountry(UserCountry.valueOf(getCountry()));
		}
		if (getRole() == null) {
			if (getUsername().equals("roman")) {
				dto.setRole(UserRole.ADMIN);
			} else if (getUsername().equals("werner") || getUsername().equals("root")) {
				dto.setRole(UserRole.DEVELOPER);
			} else {
				dto.setRole(UserRole.SHOP);
			}
		} else {
			dto.setRole(UserRole.valueOf(getRole()));
		}
		return dto;
	}

}
