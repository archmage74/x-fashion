package com.xfashion.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.xfashion.shared.ColorDTO;

@PersistenceCapable
public class Color {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String name;
	
	public Color() {
		
	}
	
	public Color(ColorDTO dto) {
		updateFromDTO(dto);
	}
	
	public void updateFromDTO(ColorDTO dto) {
		this.name = dto.getName();
	}
	
	public ColorDTO createDTO() {
		ColorDTO dto = new ColorDTO();
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
