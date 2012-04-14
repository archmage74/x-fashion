package com.xfashion.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.xfashion.shared.SizeDTO;

@PersistenceCapable
public class Size {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String name;
	
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
		dto.setName(name);
		return dto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
