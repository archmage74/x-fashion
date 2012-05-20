package com.xfashion.shared.notepad;

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

}
