package com.xfashion.client.at;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public class ArticleTypeDataProvider extends ListDataProvider<ArticleTypeDTO> {

	private boolean loaded;
	
	private HashMap<Long, ArticleTypeDTO> idToItem;

	public ArticleTypeDataProvider() {
		loaded = false;
		idToItem = new HashMap<Long, ArticleTypeDTO>();
	}
	
	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public ArticleTypeDTO resolveData(Long id) {
		return idToItem.get(id);
	}

	public void refreshResolver() {
		refreshResolver(getList());
	}
	
	private void refreshResolver(List<ArticleTypeDTO> list) {
		idToItem.clear();
		for (ArticleTypeDTO item : list) {
			idToItem.put(item.getProductNumber(), item);
		}
	}

}
