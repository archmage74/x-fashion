package com.xfashion.client;

import com.xfashion.shared.ArticleTypeDTO;

public class StyleDataProvider extends FilterDataProvider<StyleCellData> {
	
	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getStyle();
	}

}
