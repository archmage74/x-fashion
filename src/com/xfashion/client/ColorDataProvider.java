package com.xfashion.client;

import com.xfashion.shared.ArticleTypeDTO;

public class ColorDataProvider extends FilterDataProvider<ColorCellData> {
	
	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getColor();
	}

}
