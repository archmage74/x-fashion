package com.xfashion.client.at.bulk;

import com.xfashion.shared.ArticleTypeDTO;

public interface AttributeAccessor<T> {
	
	T getAttribute(ArticleTypeDTO articleType);

	void setAttribute(ArticleTypeDTO articleType, T value);
	
}
