package com.xfashion.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.FilterCellData;

public abstract class FilterDataProvider<T extends FilterCellData> extends ListDataProvider<T> {

	private boolean loaded;

	private Set<Long> filter;
	
	private HashMap<Long, T> idToItem;
	
	public abstract Long getAttributeContent(ArticleTypeDTO articleType);

	public FilterDataProvider() {
		idToItem = new HashMap<Long, T>();
		loaded = false;
		filter = new HashSet<Long>();
	}
	
	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public Set<Long> getFilter() {
		return filter;
	}

	public void setFilter(Set<Long> filter) {
		this.filter = filter;
	}
	
	public T resolveData(Long id) {
		return idToItem.get(id);
	}
	
	
	public void refreshResolver() {
		refreshResolver(getList());
	}
	
	private void refreshResolver(List<T> list) {
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
