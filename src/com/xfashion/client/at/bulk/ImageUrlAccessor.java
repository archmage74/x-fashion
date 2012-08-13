package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;

public class ImageUrlAccessor implements AttributeAccessor<String> {

	@Override
	public String getAttribute(ArticleTypeDTO articleType) {
		return articleType.getImageUrl();
	}
	
	@Override
	public void setAttribute(ArticleTypeDTO articleType, String value) {
		articleType.setImageUrl(value);
	}
	
}
