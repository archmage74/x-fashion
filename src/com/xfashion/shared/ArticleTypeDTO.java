package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ArticleTypeDTO implements IsSerializable {
	
	private String name;
	
	private String category;
	
	private String style;
	
	private String brand;
	
	private String size;
	
	private String color;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof ArticleTypeDTO)) return false;
		ArticleTypeDTO s = (ArticleTypeDTO) o;
		boolean equal = true;
		equal &= attributeEquals(name, s.getName());
		equal &= attributeEquals(category, s.getCategory());
		equal &= attributeEquals(style, s.getStyle());
		equal &= attributeEquals(brand, s.getBrand());
		equal &= attributeEquals(size, s.getSize());
		equal &= attributeEquals(color, s.getColor());
		return equal;
	}
	
	private boolean attributeEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null) {
			return false;
		}
		return o1.equals(o2);
	}

}
