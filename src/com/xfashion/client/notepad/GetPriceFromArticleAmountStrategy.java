package com.xfashion.client.notepad;

import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;

public class GetPriceFromArticleAmountStrategy<T extends ArticleAmountDTO> implements IGetPriceStrategy<T> {

	protected ArticleDataProvider<T> articleAmountDataProvider;
	protected IGetPriceStrategy<ArticleTypeDTO> priceStrategy;
	
	public GetPriceFromArticleAmountStrategy(ArticleDataProvider<T> articleAmountDataProvider, IGetPriceStrategy<ArticleTypeDTO> priceStrategy) {
		this.articleAmountDataProvider = articleAmountDataProvider;
		this.priceStrategy = priceStrategy;
	}
	
	@Override
	public Integer getPrice(T item) {
		if (item == null) {
			return null;
		}
		ArticleTypeDTO articleType = articleAmountDataProvider.retrieveArticleType(item);
		if (articleType == null) {
			return null;
		}
		return priceStrategy.getPrice(articleType);
	}
	
}
