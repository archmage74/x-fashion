package com.xfashion.client;

import com.xfashion.shared.ArticleTypeDTO;

public class BrandDataProvider extends FilterDataProvider<BrandCellData> {
	
	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getBrand();
	}

}
