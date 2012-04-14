package com.xfashion.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.xfashion.shared.BrandDTO;

@PersistenceCapable
public class Brand {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String name;
	
	public Brand() {
		
	}
	
	public Brand(BrandDTO dto) {
		updateFromDTO(dto);
	}
	
	public void updateFromDTO(BrandDTO dto) {
		this.name = dto.getName();
	}
	
	public BrandDTO createDTO() {
		BrandDTO dto = new BrandDTO();
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
