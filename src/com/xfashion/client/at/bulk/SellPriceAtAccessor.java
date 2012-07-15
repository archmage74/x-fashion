package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;

public class SellPriceAtAccessor implements AttributeAccessor<Integer> {

	@Override
	public Integer getAttribute(ArticleTypeDTO articleType) {
		return articleType.getSellPriceAt();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, Integer value) {
		articleType.setSellPriceAt(value);
	}
	
}
