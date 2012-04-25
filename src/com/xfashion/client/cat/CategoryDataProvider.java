package com.xfashion.client.cat;

import java.util.HashSet;
import java.util.Set;

import com.xfashion.client.FilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.CategoryDTO;

public class CategoryDataProvider extends FilterDataProvider<CategoryDTO> {

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
	public Long getAttributeContent(ArticleTypeDTO articleType) {
		articleType.getCategoryId();
		return null;
	}

	public Long freeCatgegoryId() {
		Set<Long> ids = new HashSet<Long>();
		for (long id = 1L; id<100L; id++) {
			ids.add(id);
		}
		for (CategoryDTO c : getList()) {
			ids.remove(c.getId());
		}
		return ids.iterator().next(); 
	}

}
