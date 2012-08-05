package com.xfashion.client.protocols;

import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.SoldArticleDTO;

public class SoldArticleDataProvider extends ArticleDataProvider<SoldArticleDTO> {

	private ArticleTypeDataProvider articleProvider;
	
	public SoldArticleDataProvider(ArticleTypeDataProvider articleProvider) {
		this.articleProvider = articleProvider;
	}
	
	@Override
	public ArticleTypeDTO retrieveArticleType(SoldArticleDTO item) {
		return articleProvider.resolveData(item.getArticleTypeKey());
	}

	@Override
	public ArticleTypeDTO retrieveArticleType(Long productNumber) {
		return articleProvider.retrieveArticleType(productNumber);
	}

}
