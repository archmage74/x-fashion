package com.xfashion.client.pricechange;

import java.util.ArrayList;
import java.util.Collection;

import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.shared.PriceChangeDTO;

public class PriceChangeArticleAmountDataProvider extends ArticleAmountDataProvider {

	Collection<PriceChangeDTO> priceChanges;
	
	public PriceChangeArticleAmountDataProvider(ArticleTypeDatabase articleTypeDatabase) {
		super(articleTypeDatabase);
		priceChanges = new ArrayList<PriceChangeDTO>();
	}

	public Collection<PriceChangeDTO> getPriceChanges() {
		return priceChanges;
	}
	
	public void setPriceChanges(Collection<PriceChangeDTO> priceChanges) {
		this.priceChanges.clear();
		this.priceChanges.addAll(priceChanges);
	}

}
