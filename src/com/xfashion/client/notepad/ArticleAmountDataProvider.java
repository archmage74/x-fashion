package com.xfashion.client.notepad;

import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.notepad.ArticleAmountDTO;

public class ArticleAmountDataProvider extends ArticleDataProvider<ArticleAmountDTO> {

	private ArticleTypeDatabase articleTypeDatabase;
	
	public ArticleAmountDataProvider(ArticleTypeDatabase articleTypeDatabase) {
		this.articleTypeDatabase = articleTypeDatabase;
	}
	
	@Override
	public ArticleTypeDTO retrieveArticleType(ArticleAmountDTO item) {
		return articleTypeDatabase.getArticleTypeProvider().resolveData(item.getArticleTypeKey());
	}

}
