package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;

public class StyleKeyAccessor implements AttributeAccessor<String> {

	@Override
	public String getAttribute(ArticleTypeDTO articleType) {
		return articleType.getStyleKey();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, String value) {
		articleType.setStyleKey(value);
	}
	
}
