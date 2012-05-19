package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;

public class CategoryKeyAccessor implements AttributeAccessor<String> {

	@Override
	public String getAttribute(ArticleTypeDTO articleType) {
		return articleType.getCategoryKey();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, String value) {
		articleType.setCategoryKey(value);
	}
	
}
