package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;

public class ColorKeyAccessor implements AttributeAccessor<String> {

	@Override
	public String getAttribute(ArticleTypeDTO articleType) {
		return articleType.getColorKey();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, String value) {
		articleType.setColorKey(value);
	}
	
}
