package com.xfashion.client.notepad;

import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;

public class ArticleAmountDataProvider extends ArticleDataProvider<ArticleAmountDTO> {

	protected ArticleTypeDataProvider articleTypeProvider;
	
	public ArticleAmountDataProvider(ArticleTypeDataProvider articleTypeProvider) {
		this.articleTypeProvider = articleTypeProvider;
	}
	
	@Override
	public ArticleTypeDTO retrieveArticleType(ArticleAmountDTO item) {
		return articleTypeProvider.resolveData(item.getArticleTypeKey());
	}

	@Override
	public ArticleTypeDTO retrieveArticleType(Long productNumber) {
		return articleTypeProvider.retrieveArticleType(productNumber);
	}

}
