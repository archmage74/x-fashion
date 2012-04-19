package com.xfashion.client;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.shared.CategoryDTO;

public class CategoryDataProvider extends ListDataProvider<CategoryDTO> {

	private boolean loaded;
	
	public CategoryDataProvider() {
		loaded = false;
	}
	
	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

}
