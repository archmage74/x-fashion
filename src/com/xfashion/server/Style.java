package com.xfashion.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.xfashion.shared.StyleDTO;

@PersistenceCapable
public class Style {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String name;
	
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