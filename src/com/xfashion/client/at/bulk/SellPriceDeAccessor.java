package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;

public class SellPriceDeAccessor implements AttributeAccessor<Integer> {

	@Override
	public Integer getAttribute(ArticleTypeDTO articleType) {
		return articleType.getSellPriceDe();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, Integer value) {
		articleType.setSellPriceDe(value);
	}
	
}
