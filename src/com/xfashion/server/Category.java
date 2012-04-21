package com.xfashion.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.xfashion.shared.CategoryDTO;

@PersistenceCapable
public class Category {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String name;
	
	@Persistent
	private String backgroundColor;
	
	@Persistent
	private String borderColor;
	
	@Persistent
	private Integer sortIndex;
	
	public Category() {
		
	}
	
	public Category(CategoryDTO dto) {
		updateFromDTO(dto);
	}
	
	public void updateFromDTO(CategoryDTO dto) {
		this.name = dto.getName();
		this.backgroundColor = dto.getBackgroundColor();
		this.borderColor = dto.getBorderColor();
		this.sortIndex = dto.getSortIndex();
	}
	
	public CategoryDTO createDTO() {
		CategoryDTO dto = new CategoryDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setBackgroundColor(backgroundColor);
		dto.setBorderColor(borderColor);
		dto.setSortIndex(sortIndex);
		return dto;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

}
