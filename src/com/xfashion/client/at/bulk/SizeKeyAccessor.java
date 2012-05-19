package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;

public class SizeKeyAccessor implements AttributeAccessor<String> {

	@Override
	public String getAttribute(ArticleTypeDTO articleType) {
		return articleType.getSizeKey();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, String value) {
		articleType.setSizeKey(value);
	}
	
}
