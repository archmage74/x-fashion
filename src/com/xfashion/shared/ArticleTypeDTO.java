package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ArticleTypeDTO extends DTO<Long> implements IsSerializable {
	
	private Long productNumber;

	private String name;
	
	private String categoryKey;
	
	private String styleKey;
	
	private String brandKey;
	
	private String sizeKey;
	
	private String colorKey;

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

	public String getCategoryKey() {
		return categoryKey;
	}

	public void setCategoryKey(String categoryKey) {
		this.categoryKey = categoryKey;
	}

	public String getStyleKey() {
		return styleKey;
	}

	public void setStyleKey(String styleKey) {
		this.styleKey = styleKey;
	}

	public String getBrandKey() {
		return brandKey;
	}

	public void setBrandKey(String brandKey) {
		this.brandKey = brandKey;
	}

	public String getSizeKey() {
		return sizeKey;
	}

	public void setSizeKey(String sizeKey) {
		this.sizeKey = sizeKey;
	}

	public String getColorKey() {
		return colorKey;
	}

	public void setColorKey(String colorKey) {
		this.colorKey = colorKey;
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
		equal &= attributeEquals(categoryKey, s.getCategoryKey());
		equal &= attributeEquals(styleKey, s.getStyleKey());
		equal &= attributeEquals(brandKey, s.getBrandKey());
		equal &= attributeEquals(sizeKey, s.getSizeKey());
		equal &= attributeEquals(colorKey, s.getColorKey());
		equal &= attributeEquals(buyPrice, s.getBuyPrice());
		equal &= attributeEquals(sellPrice, s.getSellPrice());
		return equal;
	}
	
	public ArticleTypeDTO clone() {
		ArticleTypeDTO clone = new ArticleTypeDTO();
		clone.setProductNumber(getProductNumber());
		clone.setName(getName());
		clone.setCategoryKey(getCategoryKey());
		clone.setStyleKey(getStyleKey());
		clone.setBrandKey(getBrandKey());
		clone.setSizeKey(getSizeKey());
		clone.setColorKey(getColorKey());
		clone.setBuyPrice(getBuyPrice());
		clone.setSellPrice(getSellPrice());
		clone.setImageId(getImageId());
		return clone;
	}
}
