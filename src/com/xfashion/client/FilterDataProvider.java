package com.xfashion.client;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.FilterCellData;

public abstract class FilterDataProvider<T extends FilterCellData<?>> extends ListDataProvider<T> {

	protected boolean loaded;

	protected HashMap<Object, T> idToItem;
	
	protected abstract Long getAttributeContent(ArticleTypeDTO articleType);

	public abstract List<ArticleTypeDTO> applyFilter(List<ArticleTypeDTO> articleTypes);
	
	public FilterDataProvider() {
		idToItem = new HashMap<Object, T>();
		loaded = false;
	}
	
	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public T resolveData(Long id) {
		return idToItem.get(id);
	}
	
	public void refreshResolver() {
		refreshResolver(getList());
	}
	
	protected void refreshResolver(List<T> list) {
		idToItem.clear();
		for (T item : list) {
			idToItem.put(item.getId(), item);
		}
	}
	
	@Override
	public void setList(List<T> listToWrap) {
		refreshResolver(listToWrap);
		super.setList(listToWrap);
	}
	
}
