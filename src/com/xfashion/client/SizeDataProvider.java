package com.xfashion.client;

import com.xfashion.shared.ArticleTypeDTO;

public class SizeDataProvider extends FilterDataProvider<SizeCellData> {
	
	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getSize();
	}

}
