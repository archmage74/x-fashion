package com.xfashion.client.color;

import com.xfashion.client.SimpleFilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.ColorDTO;

public class ColorDataProvider extends SimpleFilterDataProvider<ColorDTO> {
	
	public Long getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getColorId();
	}

}
