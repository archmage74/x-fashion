package com.xfashion.client.sell;

import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.SoldArticleDTO;

public class SoldArticleDataProvider extends ArticleDataProvider<SoldArticleDTO> {

	private ArticleTypeDatabase articleTypeDatabase;
	
	public SoldArticleDataProvider(ArticleTypeDatabase articleTypeDatabase) {
		this.articleTypeDatabase = articleTypeDatabase;
	}
	
	@Override
	public ArticleTypeDTO retrieveArticleType(SoldArticleDTO item) {
		return articleTypeDatabase.getArticleTypeProvider().resolveData(item.getArticleTypeKey());
	}

	@Override
	public ArticleTypeDTO retrieveArticleType(Long productNumber) {
		return articleTypeDatabase.getArticleTypeProvider().retrieveArticleType(productNumber);
	}

}
