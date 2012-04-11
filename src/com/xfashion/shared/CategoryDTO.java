package com.xfashion.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CategoryDTO implements IsSerializable {

	private String name;

	private ArrayList<String> styles;
	
	private ArrayList<String> brands;
	
	public CategoryDTO() {
		styles = new ArrayList<String>();
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<String> getStyles() {
		return styles;
	}

	public void setStyles(ArrayList<String> styles) {
		this.styles = styles;
	}

	public ArrayList<String> getBrands() {
		return brands;
	}
	
	public void setBrands(ArrayList<String> brands) {
		this.brands = brands;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof CategoryDTO)) return false;
		CategoryDTO cat = (CategoryDTO) o;
		if (name == null) {
			if (cat.getName() == null) {
				return true;
			} else {
				return false;
			}
		} else {
			if (name.equals(cat.getName())) {
				return true;
			} else {
				return false;
			}
		}
	}
}
