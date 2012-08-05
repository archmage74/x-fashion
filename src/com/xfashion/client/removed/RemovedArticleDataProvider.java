package com.xfashion.client.removed;

import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.RemovedArticleDTO;

public class RemovedArticleDataProvider extends ArticleDataProvider<RemovedArticleDTO> {

	private ArticleTypeDataProvider articleProvider;
	
	public RemovedArticleDataProvider(ArticleTypeDataProvider articleTypeDatabase) {
		this.articleProvider = articleTypeDatabase;
	}
	
	@Override
	public ArticleTypeDTO retrieveArticleType(RemovedArticleDTO item) {
		return articleProvider.resolveData(item.getArticleTypeKey());
	}

	@Override
	public ArticleTypeDTO retrieveArticleType(Long productNumber) {
		return articleProvider.retrieveArticleType(productNumber);
	}

}
