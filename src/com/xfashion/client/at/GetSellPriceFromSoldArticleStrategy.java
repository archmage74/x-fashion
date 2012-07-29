package com.xfashion.client.at;

import com.xfashion.shared.SoldArticleDTO;

public class GetSellPriceFromSoldArticleStrategy implements IGetPriceStrategy<SoldArticleDTO> {

	@Override
	public Integer getPrice(SoldArticleDTO a) {
		return a.getSellPrice();
	}
	
}
