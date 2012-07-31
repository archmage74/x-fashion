package com.xfashion.client.removed;

import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.RemovedArticleDTO;

public class RemovedArticleDataProvider extends ArticleDataProvider<RemovedArticleDTO> {

	private ArticleTypeDatabase articleTypeDatabase;
	
	public RemovedArticleDataProvider(ArticleTypeDatabase articleTypeDatabase) {
		this.articleTypeDatabase = articleTypeDatabase;
	}
	
	@Override
	public ArticleTypeDTO retrieveArticleType(RemovedArticleDTO item) {
		return articleTypeDatabase.getArticleTypeProvider().resolveData(item.getArticleTypeKey());
	}

	@Override
	public ArticleTypeDTO retrieveArticleType(Long productNumber) {
		return articleTypeDatabase.getArticleTypeProvider().retrieveArticleType(productNumber);
	}

}
