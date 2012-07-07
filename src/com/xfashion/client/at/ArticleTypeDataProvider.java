package com.xfashion.client.at;

import java.util.HashMap;
import java.util.List;

import com.xfashion.shared.ArticleTypeDTO;

public class ArticleTypeDataProvider extends ArticleDataProvider<ArticleTypeDTO> {

	private HashMap<String, ArticleTypeDTO> idToItem;
	
	private HashMap<Long, ArticleTypeDTO> productNumberToItem;

	public ArticleTypeDataProvider() {
		idToItem = new HashMap<String, ArticleTypeDTO>();
		productNumberToItem = new HashMap<Long, ArticleTypeDTO>();
	}
	
	@Override
	public ArticleTypeDTO retrieveArticleType(ArticleTypeDTO item) {
		return item;
	}

	@Override
	public ArticleTypeDTO retrieveArticleType(Long productNumber) {
		return productNumberToItem.get(productNumber);
	}

	public ArticleTypeDTO resolveData(String key) {
		return idToItem.get(key);
	}

	public void refreshResolver() {
		refreshResolver(getList());
	}
	
	private void refreshResolver(List<ArticleTypeDTO> list) {
		idToItem.clear();
		productNumberToItem.clear();
		for (ArticleTypeDTO item : list) {
			idToItem.put(item.getKey(), item);
			productNumberToItem.put(item.getProductNumber(), item);
		}
	}

}
