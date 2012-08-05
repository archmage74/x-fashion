package com.xfashion.client.stock;

import java.util.HashMap;

import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.shared.ArticleAmountDTO;

public class StockDataProvider extends ArticleAmountDataProvider {

	private HashMap<String, ArticleAmountDTO> stock;

	public StockDataProvider(ArticleTypeDataProvider articleTypeDatabase) {
		super(articleTypeDatabase);
		this.stock = new HashMap<String, ArticleAmountDTO>();
	}

	public HashMap<String, ArticleAmountDTO> getStock() {
		return stock;
	}
	
}
