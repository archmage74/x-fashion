package com.xfashion.client.style;

import com.xfashion.client.FilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public class StyleDataProvider extends FilterDataProvider<StyleCellData> {
	
	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getStyle();
	}

}
