package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ArticleTypeDTO extends DTO<Long> implements IsSerializable {
	
	private Long productNumber;

	private String name;
	
	private Long categoryId;
	
	private String styleId;
	
	private Long brandId;
	
	private String sizeKey;
	
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

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getSizeKey() {
		return sizeKey;
	}

	public void setSizeKey(String sizeKey) {
		this.sizeKey = sizeKey;
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
		equal &= attributeEquals(sizeKey, s.getSizeKey());
		equal &= attributeEquals(colorId, s.getColorId());
		equal &= attributeEquals(buyPrice, s.getBuyPrice());
		equal &= attributeEquals(sellPrice, s.getSellPrice());
		return equal;
	}
	
	public ArticleTypeDTO clone() {
		ArticleTypeDTO clone = new ArticleTypeDTO();
		clone.setProductNumber(getProductNumber());
		clone.setName(getName());
		clone.setCategoryId(getCategoryId());
		clone.setStyleId(getStyleId());
		clone.setBrandId(getBrandId());
		clone.setSizeKey(getSizeKey());
		clone.setColorId(getColorId());
		clone.setBuyPrice(getBuyPrice());
		clone.setSellPrice(getSellPrice());
		clone.setImageId(getImageId());
		return clone;
	}
}
