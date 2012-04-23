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
    private Long id;
    
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
		this.sortIndex = dto.getSortIndex();
	}
	
	public StyleDTO createDTO() {
		StyleDTO dto = new StyleDTO();
		dto.setId(id);
		dto.setName(name);
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

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}
	
}
