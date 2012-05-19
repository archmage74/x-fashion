package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;

public class SellPriceAccessor implements AttributeAccessor<Integer> {

	@Override
	public Integer getAttribute(ArticleTypeDTO articleType) {
		return articleType.getSellPrice();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, Integer value) {
		articleType.setSellPrice(value);
	}
	
}
