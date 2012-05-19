package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;

public class NameAccessor implements AttributeAccessor<String> {

	@Override
	public String getAttribute(ArticleTypeDTO articleType) {
		return articleType.getName();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, String value) {
		articleType.setName(value);
	}
	
}
