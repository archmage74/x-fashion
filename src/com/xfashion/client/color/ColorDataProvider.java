package com.xfashion.client.color;

import com.xfashion.client.FilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.ColorDTO;

public class ColorDataProvider extends FilterDataProvider<ColorDTO> {
	
	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getColor();
	}

}
