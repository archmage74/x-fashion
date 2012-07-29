package com.xfashion.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.server.user.Shop;
import com.xfashion.shared.AddedArticleDTO;
import com.xfashion.shared.ArticleAmountDTO;

@PersistenceCapable
public class AddedArticle {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	protected Key articleTypeKey;
	
	@Persistent
	protected Integer amount;

	@Persistent
	protected Date addDate;
	
	@Persistent
	protected Shop shop;
	
	public AddedArticle() {
		this.addDate = new Date();
	}
	
	public AddedArticle(AddedArticleDTO dto) {
		this.articleTypeKey = KeyFactory.stringToKey(dto.getArticleTypeKey());
		this.amount = dto.getAmount();
		this.addDate = new Date();
	}
	
	public AddedArticle(ArticleAmountDTO dto) {
		this.articleTypeKey = KeyFactory.stringToKey(dto.getArticleTypeKey());
		this.amount = dto.getAmount();
		this.addDate = new Date();
	}
	
	public AddedArticleDTO createDTO() {
		AddedArticleDTO dto = new AddedArticleDTO();
		dto.setKey(getKeyAsString());
		dto.setArticleTypeKey(getArticleTypeKeyAsString());
		dto.setAmount(getAmount());
		dto.setAddDate(getAddDate());
		if (getShop() != null) {
			dto.setShopKey(KeyFactory.keyToString(getShop().getKey()));
		}
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addDate == null) ? 0 : addDate.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((articleTypeKey == null) ? 0 : articleTypeKey.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((shop == null) ? 0 : shop.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddedArticle other = (AddedArticle) obj;
		if (addDate == null) {
			if (other.addDate != null)
				return false;
		} else if (!addDate.equals(other.addDate))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (articleTypeKey == null) {
			if (other.articleTypeKey != null)
				return false;
		} else if (!articleTypeKey.equals(other.articleTypeKey))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (shop == null) {
			if (other.shop != null)
				return false;
		} else if (!shop.equals(other.shop))
			return false;
		return true;
	}

}
