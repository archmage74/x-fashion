package com.xfashion.client.name;

import com.xfashion.client.SimpleFilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;

public class NameDataProvider extends SimpleFilterDataProvider<BrandDTO> {
	
	public Long getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getBrandId();
	}

}
