package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;

public class ImageKeyAccessor implements AttributeAccessor<String> {

	@Override
	public String getAttribute(ArticleTypeDTO articleType) {
		return articleType.getImageKey();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, String value) {
		articleType.setImageKey(value);
	}
	
}
