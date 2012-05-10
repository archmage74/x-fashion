package com.xfashion.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.StyleDTO;

@PersistenceCapable
public class Category {
	
	@PrimaryKey
	@Persistent
	private Long id;
	
	@Persistent
	private String name;
	
	@Persistent
	private String backgroundColor;
	
	@Persistent
	private String borderColor;
	
	@Persistent
	private Integer sortIndex;
	
	@Persistent
	private List<Style> styles;
	
	public Category() {
		styles = new ArrayList<Style>();
	}
	
	public Category(CategoryDTO dto) {
		this();
		updateFromDTO(dto);
	}
	
	public void updateFromDTO(CategoryDTO dto) {
		this.id = dto.getId();
		this.name = dto.getName();
		this.backgroundColor = dto.getBackgroundColor();
		this.borderColor = dto.getBorderColor();
		this.sortIndex = dto.getSortIndex();
		updateStyleList(this.styles, dto.getStyles());
	}
	
	private void updateStyleList(List<Style> styles, List<StyleDTO> dtos) {
		List<Style> stylesToRemove = new ArrayList<Style>();
		List<StyleDTO> stylesToAdd = new ArrayList<StyleDTO>(dtos);
		for (Style s : styles) {
			StyleDTO dto = findStyle(stylesToAdd, s);
			if (dto != null) {
				s.updateFromDTO(dto);
				stylesToAdd.remove(dto);
			} else {
				stylesToRemove.add(s);
			}
		}
		styles.removeAll(stylesToRemove);
		for (StyleDTO dto : stylesToAdd) {
			int index = dtos.indexOf(dto);
			Style style = new Style(dto);
			styles.add(index, style);
		}
	}
	
	private StyleDTO findStyle(List<StyleDTO> dtos, Style style) {
		for (StyleDTO dto : dtos) {
			if (dto.getId() != null && KeyFactory.stringToKey(dto.getId()).equals(style.getKey())) {
				return dto;
			}
		}
		return null;
	}
	
	public CategoryDTO createDTO() {
		CategoryDTO dto = new CategoryDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setBackgroundColor(backgroundColor);
		dto.setBorderColor(borderColor);
		dto.setSortIndex(sortIndex);
		for (Style style : styles) {
			dto.getStyles().add(style.createDTO());
		}
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

	public List<Style> getStyles() {
		return styles;
	}

	public void setStyles(List<Style> styles) {
		this.styles = styles;
	}

}
