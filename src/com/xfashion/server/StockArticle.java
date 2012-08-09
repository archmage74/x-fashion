package com.xfashion.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.server.user.Shop;
import com.xfashion.shared.ArticleAmountDTO;

@PersistenceCapable
public class StockArticle {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	protected Key articleTypeKey;
	
	@Persistent
	protected Integer amount;

	@Persistent
	protected Shop shop;
	
	public StockArticle() {
		
	}
	
	public StockArticle(ArticleAmountDTO dto) {
		updateFromDTO(dto);
	}
	
	public ArticleAmountDTO createDTO() {
		ArticleAmountDTO dto = new ArticleAmountDTO();
		dto.setKey(getKeyAsString());
		dto.setArticleTypeKey(getArticleTypeKeyAsString());
		dto.setAmount(getAmount());
//		if (getShop() != null) {
//			dto.setShopKey(KeyFactory.keyToString(getShop().getKey()));
//		}
		return dto;
	}

	public void updateFromDTO(ArticleAmountDTO dto) {
		setArticleTypeKey(KeyFactory.stringToKey(dto.getArticleTypeKey()));
		setAmount(dto.getAmount());
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

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

}
