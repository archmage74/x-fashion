package com.xfashion.client.at.price;

import com.xfashion.shared.ArticleTypeDTO;

public class GetDePriceFromArticleTypeStrategy implements IGetPriceStrategy<ArticleTypeDTO> {

	@Override
	public Integer getPrice(ArticleTypeDTO a) {
		return a.getSellPriceDe();
	}

}
