package com.xfashion.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NotepadDTO implements Serializable, IsSerializable {
	
	private static final long serialVersionUID = -5274228818084728571L;
	
	private String key;
	
	private String name;
	
	private Date creationDate;
	
	private List<ArticleAmountDTO> articles;

	public NotepadDTO() {
		creationDate = new Date();
		articles = new ArrayList<ArticleAmountDTO>();
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<ArticleAmountDTO> getArticles() {
		return articles;
	}
	
	public void addArticle(ArticleTypeDTO articleType) {
		addArticle(articleType, 1);
	}
	
	public void addArticle(ArticleTypeDTO articleType, Integer amount) {
		ArticleAmountDTO articleAmount = retrieveArticleAmount(articleType.getKey());
		articleAmount.increaseAmount(amount);
	}
	
	public void setArticles(List<ArticleAmountDTO> articles) {
		this.articles = articles;
	}
	
	public void deductArticleType(ArticleTypeDTO articleType) {
		deductArticleType(articleType, 1);
	}
	
	public void deductArticleType(ArticleTypeDTO articleType, Integer amount) {
		ArticleAmountDTO articleAmount = retrieveArticleAmount(articleType.getKey());
		if (articleAmount.getAmount() > amount) {
			articleAmount.decreaseAmount(amount);
		} else {
			articles.remove(articleAmount);
		}
	}
	
	public ArticleAmountDTO retrieveArticleAmount(String articleTypeKey) {
		for (ArticleAmountDTO a : articles) {
			if (articleTypeKey.equals(a.getArticleTypeKey())) {
				return a;
			}
		}
		ArticleAmountDTO a = new ArticleAmountDTO(articleTypeKey, 0);
		articles.add(a);
		return a;
	}

}
