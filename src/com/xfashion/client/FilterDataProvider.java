package com.xfashion.client;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public abstract class FilterDataProvider<T extends FilterCellData> extends ListDataProvider<T> {

	private boolean loaded;
	
	public abstract String getAttributeContent(ArticleTypeDTO articleType);

	public FilterDataProvider() {
		loaded = false;
	}
	
	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

}
