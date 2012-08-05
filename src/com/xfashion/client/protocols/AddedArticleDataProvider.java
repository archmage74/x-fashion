package com.xfashion.client.protocols;

import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.shared.AddedArticleDTO;
import com.xfashion.shared.ArticleTypeDTO;

public class AddedArticleDataProvider extends ArticleDataProvider<AddedArticleDTO> {

	private ArticleTypeDataProvider articleProvider;
	
	public AddedArticleDataProvider(ArticleTypeDataProvider articleProvider) {
		this.articleProvider = articleProvider;
	}
	
	@Override
	public ArticleTypeDTO retrieveArticleType(AddedArticleDTO item) {
		return articleProvider.resolveData(item.getArticleTypeKey());
	}

	@Override
	public ArticleTypeDTO retrieveArticleType(Long productNumber) {
		return articleProvider.retrieveArticleType(productNumber);
	}

}
