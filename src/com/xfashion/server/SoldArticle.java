package com.xfashion.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.SoldArticleDTO;

@PersistenceCapable
public class SoldArticle {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	protected Key articleTypeKey;
	
	@Persistent
	protected String articleName;

	@Persistent
	protected String category;
	
	@Persistent
	protected String style;
	
	@Persistent
	protected String brand;
	
	@Persistent
	protected String color;
	
	@Persistent
	protected String size;
	
	@Persistent
	protected Integer amount;

	@Persistent
	protected Key shopKey;
	
	@Persistent
	protected String shopName;
	
	@Persistent
	protected Integer buyPrice;

	@Persistent
	protected Integer originalSellPrice;
	
	@Persistent
	protected Integer sellPrice;

	@Persistent
	protected Date sellDate;
	
	@Persistent
	protected Key promoKey;
	
	public SoldArticle() {
		this.sellDate = new Date();
	}
	
	public SoldArticle(SoldArticleDTO dto) {
		this.articleTypeKey = KeyFactory.stringToKey(dto.getArticleTypeKey());
		this.articleName = dto.getArticleName();
		this.category = dto.getCategory();
		this.style = dto.getStyle();
		this.brand = dto.getBrand();
		this.color = dto.getColor();
		this.size = dto.getSize();
		this.amount = dto.getAmount();
		this.shopKey = KeyFactory.stringToKey(dto.getShopKey());
		this.shopName = dto.getShopName();
		this.buyPrice = dto.getBuyPrice();
		this.sellPrice = dto.getSellPrice();
		this.originalSellPrice = dto.getOriginalSellPrice();
		this.sellDate = new Date();
		if (dto.getPromoKey() != null) {
			this.promoKey = KeyFactory.stringToKey(dto.getPromoKey());
		}
	}
	
	public SoldArticleDTO createDTO() {
		SoldArticleDTO dto = new SoldArticleDTO();
		dto.setKey(getKeyAsString());
		dto.setArticleTypeKey(getArticleTypeKeyAsString());
		dto.setArticleName(getArticleName());
		dto.setCategory(getCategory());
		dto.setStyle(getStyle());
		dto.setBrand(getBrand());
		dto.setColor(getColor());
		dto.setSize(getSize());
		dto.setAmount(getAmount());
		dto.setShopKey(getShopKeyAsString());
		dto.setShopName(getShopName());
		dto.setBuyPrice(getBuyPrice());
		dto.setSellPrice(getSellPrice());
		dto.setOriginalSellPrice(getOriginalSellPrice());
		dto.setSellDate(getSellDate());
		dto.setPromoKey(getPromoKeyAsString());
		return dto;
	}

	public Key getKey() {
		return key;
	}

	public String getKeyAsString() {
		return KeyFactory.keyToString(key);
	}

	public Key getArticleTypeKey() {
		return articleTypeKey;
	}

	public String getArticleTypeKeyAsString() {
		return KeyFactory.keyToString(articleTypeKey);
	}

	public void setArticleTypeKey(Key articleTypeKey) {
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

	public Key getShopKey() {
		return shopKey;
	}

	public String getShopKeyAsString() {
		return KeyFactory.keyToString(shopKey);
	}

	public void setShopKey(Key shopKey) {
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

	public Key getPromoKey() {
		return promoKey;
	}

	public String getPromoKeyAsString() {
		if (promoKey != null) {
			return KeyFactory.keyToString(promoKey);
		} else {
			return null;
		}
	}

	public void setPromoKey(Key promoKey) {
		this.promoKey = promoKey;
	}

}
