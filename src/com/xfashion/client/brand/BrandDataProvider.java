package com.xfashion.client.brand;

import com.xfashion.client.SimpleFilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;

public class BrandDataProvider extends SimpleFilterDataProvider<BrandDTO> {
	
	public Long getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getBrandId();
	}

}
