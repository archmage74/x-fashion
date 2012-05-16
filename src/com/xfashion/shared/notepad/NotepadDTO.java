package com.xfashion.shared.notepad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.xfashion.shared.ArticleTypeDTO;

public class NotepadDTO implements Serializable, IsSerializable {
	
	private static final long serialVersionUID = -5274228818084728571L;
	
	private Long id;
	
	private List<ArticleAmountDTO> articles;

	public NotepadDTO() {
		articles = new ArrayList<ArticleAmountDTO>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ArticleAmountDTO> getArticleTypes() {
		return articles;
	}

	public void setArticleTypes(List<ArticleAmountDTO> articles) {
		this.articles = articles;
	}

	public void addArticleType(ArticleTypeDTO articleType) {
		addArticleType(articleType, 1);
	}
	
	public void addArticleType(ArticleTypeDTO articleType, Integer amount) {
		ArticleAmountDTO articleAmount = retrieveArticleAmount(articleType.getProductNumber());
		articleAmount.increaseAmount(amount);
	}
	
	public void deductArticleType(ArticleTypeDTO articleType) {
		deductArticleType(articleType, 1);
	}
	
	public void deductArticleType(ArticleTypeDTO articleType, Integer amount) {
		ArticleAmountDTO articleAmount = retrieveArticleAmount(articleType.getProductNumber());
		if (articleAmount.getAmount() > amount) {
			articleAmount.decreaseAmount(amount);
		} else {
			articles.remove(articleAmount);
		}
	}
	
	public ArticleAmountDTO retrieveArticleAmount(Long productNumber) {
		for (ArticleAmountDTO a : articles) {
			if (productNumber.equals(a.getProductNumber())) {
				return a;
			}
		}
		ArticleAmountDTO a = new ArticleAmountDTO(productNumber, 0);
		articles.add(a);
		return a;
	}
	
}
