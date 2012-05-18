package com.xfashion.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.StyleDTO;

@PersistenceCapable
public class Category {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Integer categoryNumber;
	
	@Persistent
	private String name;
	
	@Persistent
	private Integer sortIndex;
	
	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="sortIndex asc"))
	private List<Style> styles;
	
	public Category() {
		styles = new ArrayList<Style>();
	}
	
	public Category(CategoryDTO dto) {
		this();
		updateFromDTO(dto);
	}
	
	public void updateFromDTO(CategoryDTO dto) {
		this.name = dto.getName();
		updateStyleList(dto.getStyles());
	}
	
	public void updateStyleList(List<StyleDTO> dtos) {
		List<Style> toRemove = new ArrayList<Style>(styles);
		List<Style> toAdd = new ArrayList<Style>();
		int idx = 0;
		for (StyleDTO dto : dtos) {
			Style item = findStyle(styles, dto);
			if (item == null) {
				item = new Style(dto);
				toAdd.add(item);
			} else {
				toRemove.remove(item);
				item.updateFromDTO(dto);
			}
			item.setSortIndex(idx++);
		}
		for (Style item : toRemove) {
			styles.remove(item);
		}
		this.styles.addAll(toAdd);
	}
	
	private Style findStyle(List<Style> list, StyleDTO dto) {
		for (Style item : list) {
			if (dto.getKey() != null && KeyFactory.stringToKey(dto.getKey()).equals(item.getKey())) {
				return item;
			}
		}
		return null;
	}

	
	public CategoryDTO createDTO() {
		CategoryDTO dto = new CategoryDTO();
		dto.setKey(getKeyString());
		dto.setName(getName());
		for (Style style : styles) {
			dto.getStyles().add(style.createDTO());
		}
		return dto;
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

	public List<Style> getStyles() {
		return styles;
	}

	public void setStyles(List<Style> styles) {
		this.styles = styles;
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

	public Integer getCategoryNumber() {
		return categoryNumber;
	}

	public void setCategoryNumber(Integer categoryNumber) {
		this.categoryNumber = categoryNumber;
	}

}
