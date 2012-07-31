package com.xfashion.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.server.user.Shop;
import com.xfashion.shared.RemovedArticleDTO;
import com.xfashion.shared.ArticleAmountDTO;

@PersistenceCapable
public class RemovedArticle {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	protected Key articleTypeKey;
	
	@Persistent
	protected Integer amount;

	@Persistent
	protected Date removeDate;
	
	@Persistent
	protected Shop shop;
	
	public RemovedArticle() {
		this.removeDate = new Date();
	}
	
	public RemovedArticle(RemovedArticleDTO dto) {
		this.articleTypeKey = KeyFactory.stringToKey(dto.getArticleTypeKey());
		this.amount = dto.getAmount();
		this.removeDate = new Date();
	}
	
	public RemovedArticle(ArticleAmountDTO dto) {
		this.articleTypeKey = KeyFactory.stringToKey(dto.getArticleTypeKey());
		this.amount = dto.getAmount();
		this.removeDate = new Date();
	}
	
	public RemovedArticleDTO createDTO() {
		RemovedArticleDTO dto = new RemovedArticleDTO();
		dto.setKey(getKeyAsString());
		dto.setArticleTypeKey(getArticleTypeKeyAsString());
		dto.setAmount(getAmount());
		dto.setRemoveDate(getRemoveDate());
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

	public Date getRemoveDate() {
		return removeDate;
	}

	public void setRemoveDate(Date removeDate) {
		this.removeDate = removeDate;
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
		result = prime * result + ((removeDate == null) ? 0 : removeDate.hashCode());
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
		RemovedArticle other = (RemovedArticle) obj;
		if (removeDate == null) {
			if (other.removeDate != null)
				return false;
		} else if (!removeDate.equals(other.removeDate))
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
