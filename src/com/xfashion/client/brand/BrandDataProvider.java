package com.xfashion.client.brand;

import com.xfashion.client.FilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;

public class BrandDataProvider extends FilterDataProvider<BrandDTO> {
	
	public Long getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getBrandId();
	}

}
