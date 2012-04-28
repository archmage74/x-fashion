package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ArticleTypeDTO extends DTO implements IsSerializable {
	
	private Long productNumber;

	private String name;
	
	private Long categoryId;
	
	private Long styleId;
	
	private Long brandId;
	
	private Long sizeId;
	
	private Long colorId;

	private Integer buyPrice;
	
	private Integer sellPrice;
	
	private Long imageId;

	public Long getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Long productNumber) {
		this.productNumber = productNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getStyleId() {
		return styleId;
	}

	public void setStyleId(Long styleId) {
		this.styleId = styleId;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getSizeId() {
		return sizeId;
	}

	public void setSizeId(Long sizeId) {
		this.sizeId = sizeId;
	}

	public Long getColorId() {
		return colorId;
	}

	public void setColorId(Long colorId) {
		this.colorId = colorId;
	}

	public Integer getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Integer buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Integer getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Integer sellPrice) {
		this.sellPrice = sellPrice;
	}
	
	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof ArticleTypeDTO)) return false;
		ArticleTypeDTO s = (ArticleTypeDTO) o;
		boolean equal = true;
		equal &= attributeEquals(productNumber, s.getProductNumber());
		equal &= attributeEquals(name, s.getName());
		equal &= attributeEquals(categoryId, s.getCategoryId());
		equal &= attributeEquals(styleId, s.getStyleId());
		equal &= attributeEquals(brandId, s.getBrandId());
		equal &= attributeEquals(sizeId, s.getSizeId());
		equal &= attributeEquals(colorId, s.getColorId());
		equal &= attributeEquals(buyPrice, s.getBuyPrice());
		equal &= attributeEquals(sellPrice, s.getSellPrice());
		return equal;
	}

}
