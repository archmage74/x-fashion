package com.xfashion.client.cat;

import com.xfashion.client.FilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public class CategoryDataProvider extends FilterDataProvider<CategoryCellData> {

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

	@Override
	public String getAttributeContent(ArticleTypeDTO articleType) {
		articleType.getCategory();
		return null;
	}

}
