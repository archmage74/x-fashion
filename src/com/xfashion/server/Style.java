package com.xfashion.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.StyleDTO;

@PersistenceCapable
public class Style {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private String name;
	
	@Persistent
	private Integer sortIndex;
	
	public Style() {
		
	}
	
	public Style(StyleDTO dto) {
		updateFromDTO(dto);
	}
	
	public void updateFromDTO(StyleDTO dto) {
		this.name = dto.getName();
	}
	
	public StyleDTO createDTO() {
		StyleDTO dto = new StyleDTO();
		dto.setKey(getKeyString());
		dto.setName(name);
		return dto;
	}

	public Key getKey() {
		return key;
	}

	public String getKeyString() {
		return KeyFactory.keyToString(key);
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

}
