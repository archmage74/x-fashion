package com.xfashion.client.color;

import com.xfashion.client.FilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public class ColorDataProvider extends FilterDataProvider<ColorCellData> {
	
	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getColor();
	}

}
