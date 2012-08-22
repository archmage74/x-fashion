package com.xfashion.client.stock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.xfashion.client.SimpleFilterDataProvider;
import com.xfashion.client.at.ArticleFilterProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;

public class StockFilterProvider extends ArticleFilterProvider {

	protected StockDataProvider stockProvider;
	
	public StockFilterProvider(ArticleTypeDataProvider articleTypeProvider, StockDataProvider stockProvider) {
		super(articleTypeProvider);
		this.stockProvider = stockProvider;
	}

	public StockDataProvider getStockProvider() {
		return stockProvider;
	}

	public void setStockProvider(StockDataProvider stockProvider) {
		this.stockProvider = stockProvider;
	}

	@Override
	public Collection<ArticleTypeDTO> getAllArticleTypes() {
		Collection<ArticleAmountDTO> amounts = stockProvider.getStock().values();
		ArrayList<ArticleTypeDTO> articles = new ArrayList<ArticleTypeDTO>(amounts.size());
		for (ArticleAmountDTO a : amounts) {
			ArticleTypeDTO at = stockProvider.retrieveArticleType(a);
			if (at != null) {
				articles.add(at);
			}
		}
		return articles;
	}
	
	@Override
	public void updateProviders() {
		categoryProvider.refresh();
		updateStyleProvider();
		updateProvider(brandProvider);
		updateProvider(colorProvider);
		updateProvider(sizeProvider);
		nameProvider.updateAvailableArticleNames();
	}

	@Override
	public void updateStyleProvider() {
		categoryProvider.refreshResolver();
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypeProvider.getAllArticleTypes());
		temp = categoryProvider.applyCategoryFilter(temp);
		temp = sizeProvider.applyFilter(temp);
		temp = colorProvider.applyFilter(temp);
		temp = nameProvider.applyFilter(temp);
		categoryProvider.updateStyles(temp, stockProvider.getStock());
	}

	@Override
	public void updateProvider(SimpleFilterDataProvider<?> providerToUpdate) {
		providerToUpdate.refreshResolver();
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypeProvider.getAllArticleTypes());
		temp = categoryProvider.applyFilter(temp);
		if (brandProvider != providerToUpdate) {
			temp = brandProvider.applyFilter(temp);
		}
		if (sizeProvider != providerToUpdate) {
			temp = sizeProvider.applyFilter(temp);
		}
		if (colorProvider != providerToUpdate) {
			temp = colorProvider.applyFilter(temp);
		}
		temp = nameProvider.applyFilter(temp);
		providerToUpdate.update(temp, stockProvider.getStock());
	}

}
