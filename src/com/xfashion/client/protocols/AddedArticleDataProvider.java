package com.xfashion.client.protocols;

import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.shared.AddedArticleDTO;
import com.xfashion.shared.ArticleTypeDTO;

public class AddedArticleDataProvider extends ArticleDataProvider<AddedArticleDTO> {

	private ArticleTypeDatabase articleTypeDatabase;
	
	public AddedArticleDataProvider(ArticleTypeDatabase articleTypeDatabase) {
		this.articleTypeDatabase = articleTypeDatabase;
	}
	
	@Override
	public ArticleTypeDTO retrieveArticleType(AddedArticleDTO item) {
		return articleTypeDatabase.getArticleTypeProvider().resolveData(item.getArticleTypeKey());
	}

	@Override
	public ArticleTypeDTO retrieveArticleType(Long productNumber) {
		return articleTypeDatabase.getArticleTypeProvider().retrieveArticleType(productNumber);
	}

}
