package com.xfashion.server.notepad;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.notepad.ArticleAmountDTO;

@PersistenceCapable
public class ArticleAmount {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

	@Persistent
	protected Key articleTypeKey;
	
	@Persistent
	protected Integer amount;

	public ArticleAmount() {
		
	}

	public ArticleAmount(ArticleAmountDTO dto) {
		updateFromDTO(dto);
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

	public void updateFromDTO(ArticleAmountDTO dto) {
		setArticleTypeKey(KeyFactory.stringToKey(dto.getArticleTypeKey()));
		setAmount(dto.getAmount());
	}

	public ArticleAmountDTO createDTO() {
		ArticleAmountDTO dto = new ArticleAmountDTO();
		dto.setKey(getKeyAsString());
		dto.setArticleTypeKey(getArticleTypeKeyAsString());
		dto.setAmount(getAmount());
		return dto;
	}

}
