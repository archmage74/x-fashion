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
    private String css;
	
	@Persistent
	private String name;
	
	@Persistent
	private String backgroundColor;
	
	@Persistent
	private String borderColor;
	
	public Category() {
		
	}
	
	public Category(CategoryDTO dto) {
		updateFromDTO(dto);
	}
	
	public void updateFromDTO(CategoryDTO dto) {
		this.css = dto.getCss();
		this.name = dto.getName();
		this.backgroundColor = dto.getBackgroundColor();
		this.borderColor = dto.getBorderColor();
	}
	
	public CategoryDTO createDTO() {
		CategoryDTO dto = new CategoryDTO();
		dto.setCss(css);
		dto.setName(name);
		dto.setBackgroundColor(backgroundColor);
		dto.setBorderColor(borderColor);
		return dto;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCss() {
		return css;
	}
	
	public void setCss(String css) {
		this.css = css;
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
}
