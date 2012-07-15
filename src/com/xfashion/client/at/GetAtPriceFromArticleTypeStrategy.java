package com.xfashion.client.at;

import com.xfashion.shared.ArticleTypeDTO;

public class GetAtPriceFromArticleTypeStrategy implements IGetPriceStrategy<ArticleTypeDTO> {

	@Override
	public Integer getPrice(ArticleTypeDTO a) {
		return a.getSellPriceAt();
	}

}
