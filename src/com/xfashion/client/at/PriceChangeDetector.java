package com.xfashion.client.at;

import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.PriceChangeDTO;

public class PriceChangeDetector {

	protected static PriceChangeDetector priceChangeDetector;
	
	protected PriceChangeDetector() {
		
	}
	
	public static PriceChangeDetector getInstance() {
		if (priceChangeDetector == null) {
			priceChangeDetector = new PriceChangeDetector();
		}
		return priceChangeDetector;
	}
	
	public PriceChangeDTO detectSellPriceChange(ArticleTypeDTO original, ArticleTypeDTO altered) {
		PriceChangeDTO priceChange = null;
		if (original.getSellPriceAt() != null && !original.getSellPriceAt().equals(altered.getSellPriceAt())) {
			priceChange = new PriceChangeDTO();
			priceChange.setSellPriceAt(original.getSellPriceAt());
		}
		if (original.getSellPriceDe() != null && !original.getSellPriceDe().equals(altered.getSellPriceDe())) {
			if (priceChange == null) {
				priceChange = new PriceChangeDTO();
			}
			priceChange.setSellPriceDe(original.getSellPriceDe());
		}
		
		if (priceChange != null) {
			priceChange.setArticleTypeKey(original.getKey());
		}
		
		return priceChange;
	}

}
