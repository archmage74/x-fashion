package com.xfashion.client.notepad;

import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;

public class ArticleAmountDataProvider extends ArticleDataProvider<ArticleAmountDTO> {

	protected ArticleTypeDataProvider articleTypeDatabase;
	
	public ArticleAmountDataProvider(ArticleTypeDataProvider articleTypeDatabase) {
		this.articleTypeDatabase = articleTypeDatabase;
	}
	
	@Override
	public ArticleTypeDTO retrieveArticleType(ArticleAmountDTO item) {
		return articleTypeDatabase.resolveData(item.getArticleTypeKey());
	}

	@Override
	public ArticleTypeDTO retrieveArticleType(Long productNumber) {
		return articleTypeDatabase.retrieveArticleType(productNumber);
	}

}
