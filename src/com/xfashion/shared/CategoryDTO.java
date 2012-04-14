package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CategoryDTO implements IsSerializable {

	private String css;
	
	private String name;
	
	private String borderColor;
	
	private String backgroundColor;

	public CategoryDTO() {

	}

	public CategoryDTO(String css, String name, String backgroundColor, String borderColor) {
		this.css = css;
		this.name = name;
		this.backgroundColor = backgroundColor;
		this.borderColor = borderColor;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
}
