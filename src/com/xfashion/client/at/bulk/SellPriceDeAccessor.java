package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.PriceChangeDTO;

public class SellPriceDeAccessor implements PriceAttributeAccessor {

	@Override
	public Integer getAttribute(ArticleTypeDTO articleType) {
		return articleType.getSellPriceDe();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, Integer value) {
		articleType.setSellPriceDe(value);
	}
	
	@Override
	public void setOldPrice(PriceChangeDTO priceChange, Integer value) {
		priceChange.setSellPriceDe(value);
	}	

}
