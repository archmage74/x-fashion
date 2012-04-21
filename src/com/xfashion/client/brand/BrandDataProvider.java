package com.xfashion.client.brand;

import com.xfashion.client.FilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public class BrandDataProvider extends FilterDataProvider<BrandCellData> {
	
	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getBrand();
	}

}
