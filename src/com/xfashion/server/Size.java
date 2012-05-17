package com.xfashion.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.SizeDTO;

@PersistenceCapable
public class Size {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String name;

	private Integer sortIndex;
	
	public Size() {

	}

	public Size(SizeDTO dto) {
		updateFromDTO(dto);
	}

	public void updateFromDTO(SizeDTO dto) {
		this.name = dto.getName();
	}

	public SizeDTO createDTO() {
		SizeDTO dto = new SizeDTO();
		dto.setKey(getKeyString());
		dto.setName(name);
		return dto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

}
