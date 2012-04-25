package com.xfashion.client.style;

import com.xfashion.client.FilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.StyleDTO;

public class StyleDataProvider extends FilterDataProvider<StyleDTO> {
	
	public Long getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getStyleId();
	}

}
