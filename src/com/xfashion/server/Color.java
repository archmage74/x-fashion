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
	private Long id;
	
	@Persistent
	private String name;
	
	@Persistent
	private Integer sortIndex;
	
	public Color() {
		
	}
	
	public Color(ColorDTO dto) {
		updateFromDTO(dto);
	}
	
	public void updateFromDTO(ColorDTO dto) {
		this.name = dto.getName();
		this.sortIndex = dto.getSortIndex();
	}
	
	public ColorDTO createDTO() {
		ColorDTO dto = new ColorDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setSortIndex(sortIndex);
		return dto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	
}
