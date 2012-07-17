package com.xfashion.shared;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ArticleAmountDTO implements Serializable, IsSerializable {

	private static final long serialVersionUID = 8130078401724950984L;

	protected String key;
	
	protected String articleTypeKey;
	
	protected Integer amount;

	public ArticleAmountDTO() {
		
	}
	
	public ArticleAmountDTO(String articleTypeKey, Integer amount) {
		this.articleTypeKey = articleTypeKey;
		this.amount = amount;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((articleTypeKey == null) ? 0 : articleTypeKey.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		ArticleAmountDTO other = (ArticleAmountDTO) obj;
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
		return true;
	}

}
