package com.xfashion.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SoldArticleDTO implements IsSerializable {

	protected String key;
	
	protected String articleTypeKey;
	
	protected String articleName;
	
	protected Integer amount;
	
	protected String shopKey;
	
	protected String shopName;

	protected Integer buyPrice;

	protected Integer sellPrice;
	
	protected Date sellDate;

	public SoldArticleDTO() {
		
	}
	
	public SoldArticleDTO(ArticleTypeDTO articleTypeDTO, Integer sellPrice, ShopDTO shopDTO, Integer amount) {
		this.articleTypeKey = articleTypeDTO.getKey();
		this.articleName = articleTypeDTO.getName();
		this.amount = amount;
		this.shopKey = shopDTO.getKeyString();
		this.shopName = shopDTO.getName();
		this.buyPrice = articleTypeDTO.getBuyPrice();
		this.sellPrice = sellPrice;
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

	public Date getSellDate() {
		return sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}

}
