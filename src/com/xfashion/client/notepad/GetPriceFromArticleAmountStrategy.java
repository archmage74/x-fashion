package com.xfashion.client.notepad;

import com.xfashion.client.at.IGetPriceStrategy;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;

public class GetPriceFromArticleAmountStrategy implements IGetPriceStrategy<ArticleAmountDTO> {

	protected ArticleAmountDataProvider articleAmountDataProvider;
	protected IGetPriceStrategy<ArticleTypeDTO> priceStrategy;
	
	public GetPriceFromArticleAmountStrategy(ArticleAmountDataProvider articleAmountDataProvider, IGetPriceStrategy<ArticleTypeDTO> priceStrategy) {
		this.articleAmountDataProvider = articleAmountDataProvider;
		this.priceStrategy = priceStrategy;
	}
	
	@Override
	public Integer getPrice(ArticleAmountDTO item) {
		ArticleTypeDTO articleType = articleAmountDataProvider.retrieveArticleType(item);
		return priceStrategy.getPrice(articleType);
	}
	
}
