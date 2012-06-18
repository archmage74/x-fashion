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
	protected Integer amount;

	@Persistent
	protected Key shopKey;
	
	@Persistent
	protected String shopName;
	
	@Persistent
	protected Integer buyPrice;

	@Persistent
	protected Integer sellPrice;

	@Persistent
	protected Date sellDate;
	
	public SoldArticle() {
		
	}
	
	public SoldArticle(SoldArticleDTO dto) {
		this.articleTypeKey = KeyFactory.stringToKey(dto.getArticleTypeKey());
		this.articleName = dto.getArticleName();
		this.amount = dto.getAmount();
		this.shopKey = KeyFactory.stringToKey(dto.getShopKey());
		this.shopName = dto.getShopName();
		this.buyPrice = dto.getBuyPrice();
		this.sellPrice = dto.getSellPrice();
		this.sellDate = new Date();
	}
	
	public SoldArticleDTO createDTO() {
		SoldArticleDTO dto = new SoldArticleDTO();
		dto.setKey(getKeyAsString());
		dto.setArticleTypeKey(getArticleTypeKeyAsString());
		dto.setArticleName(getArticleName());
		dto.setAmount(getAmount());
		dto.setShopKey(getShopKeyAsString());
		dto.setShopName(getShopName());
		dto.setBuyPrice(getBuyPrice());
		dto.setSellPrice(getSellPrice());
		dto.setSellDate(getSellDate());
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
		return KeyFactory.keyToString(articleTypeKey);
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

	public Date getSellDate() {
		return sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}

}
