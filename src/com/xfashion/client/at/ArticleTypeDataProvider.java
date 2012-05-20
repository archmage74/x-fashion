package com.xfashion.client.at;

import java.util.HashMap;
import java.util.List;

import com.xfashion.shared.ArticleTypeDTO;

public class ArticleTypeDataProvider extends ArticleDataProvider<ArticleTypeDTO> {

	private boolean loaded;
	
	private HashMap<String, ArticleTypeDTO> idToItem;

	public ArticleTypeDataProvider() {
		loaded = false;
		idToItem = new HashMap<String, ArticleTypeDTO>();
	}
	
	public ArticleTypeDTO retrieveArticleType(ArticleTypeDTO item) {
		return item;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public ArticleTypeDTO resolveData(String key) {
		return idToItem.get(key);
	}

	public void refreshResolver() {
		refreshResolver(getList());
	}
	
	private void refreshResolver(List<ArticleTypeDTO> list) {
		idToItem.clear();
		for (ArticleTypeDTO item : list) {
			idToItem.put(item.getKey(), item);
		}
	}

}
