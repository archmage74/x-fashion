package com.xfashion.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SoldArticleDTO implements IsSerializable {

	protected String key;
	
	protected String articleTypeKey;
	
	protected String articleName;
	
	protected String category;
	
	protected String style;
	
	protected String brand;
	
	protected String color;
	
	protected String size;
	
	protected Integer amount;
	
	protected String shopKey;
	
	protected String shopName;

	protected Integer buyPrice;

	protected Integer sellPrice;
	
	/**
	 * sell price without taking promo into account
	 */
	protected Integer originalSellPrice;
	
	protected Date sellDate;
	
	protected String promoKey;

	public SoldArticleDTO() {
		
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getArticleTypeKey() {
		return articleTypeKey;
	}

	public void setArticleTypeKey(String articleTypeKey) {
		this.articleTypeKey = articleTypeKey;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStyle() {
		return style;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public void increaseAmount() {
		this.amount ++;
	}

	public void increaseAmount(Integer amount) {
		this.amount += amount;
	}

	public void decreaseAmount(Integer amount) {
		this.amount -= amount;
	}

	public String getShopKey() {
		return shopKey;
	}

	public void setShopKey(String shopKey) {
		this.shopKey = shopKey;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
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

	public Integer getOriginalSellPrice() {
		return originalSellPrice;
	}

	public void setOriginalSellPrice(Integer originalSellPrice) {
		this.originalSellPrice = originalSellPrice;
	}

	public Date getSellDate() {
		return sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}

	public String getPromoKey() {
		return promoKey;
	}

	public void setPromoKey(String promoKey) {
		this.promoKey = promoKey;
	}

}
