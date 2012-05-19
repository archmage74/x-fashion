package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;

public class BrandKeyAccessor implements AttributeAccessor<String> {

	@Override
	public String getAttribute(ArticleTypeDTO articleType) {
		return articleType.getBrandKey();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, String value) {
		articleType.setBrandKey(value);
	}
	
}
