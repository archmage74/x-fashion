package com.xfashion.client.at;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public class ArticleTypeDataProvider extends ListDataProvider<ArticleTypeDTO> {

	private boolean loaded;
	
	public ArticleTypeDataProvider() {
		loaded = false;
	}
	
	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

}
