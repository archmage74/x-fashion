package com.xfashion.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.FilterCellData;

public abstract class FilterDataProvider<T extends FilterCellData> extends ListDataProvider<T> {

	private boolean loaded;

	private Set<String> filter;
	
	public abstract String getAttributeContent(ArticleTypeDTO articleType);

	public FilterDataProvider() {
		loaded = false;
		filter = new HashSet<String>();
	}
	
	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public Set<String> getFilter() {
		return filter;
	}

	public void setFilter(Set<String> filter) {
		this.filter = filter;
	}

}
