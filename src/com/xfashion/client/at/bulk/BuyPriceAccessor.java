package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;

public class BuyPriceAccessor implements AttributeAccessor<Integer> {

	@Override
	public Integer getAttribute(ArticleTypeDTO articleType) {
		return articleType.getBuyPrice();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, Integer value) {
		articleType.setBuyPrice(value);
	}
	
}
