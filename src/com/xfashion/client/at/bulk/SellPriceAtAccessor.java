package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.PriceChangeDTO;

public class SellPriceAtAccessor implements PriceAttributeAccessor {

	@Override
	public Integer getAttribute(ArticleTypeDTO articleType) {
		return articleType.getSellPriceAt();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, Integer value) {
		articleType.setSellPriceAt(value);
	}
	
	@Override
	public void setOldPrice(PriceChangeDTO priceChange, Integer value) {
		priceChange.setSellPriceAt(value);
	}	

}
