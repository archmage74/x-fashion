package com.xfashion.server;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable
public class ArticleType implements IsSerializable {
	
	@PrimaryKey
	@Persistent
	private Long productNumber;

	@Persistent
	private String name;
	
	@Persistent
	private String category;
	
	@Persistent
	private String style;
	
	@Persistent
	private String brand;
	
	@Persistent
	private String size;
	
	@Persistent
	private String color;

	@Persistent
	private Integer price;
	
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
		if (!(o instanceof ArticleType)) return false;
		ArticleType s = (ArticleType) o;
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

	public Long getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Long productNumber) {
		this.productNumber = productNumber;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
