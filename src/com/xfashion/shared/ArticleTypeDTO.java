package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ArticleTypeDTO extends DTO implements IsSerializable {
	
	private Long productNumber;

	private String name;
	
	private String categoryKey;
	
	private String styleKey;
	
	private String brandKey;
	
	private String sizeKey;
	
	private String colorKey;

	private Integer buyPrice;
	
	private Integer sellPrice;
	
	private String imageKey;

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
	
	public String getImageKey() {
		return imageKey;
	}

	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
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
		clone.setImageKey(getImageKey());
		return clone;
	}

}
