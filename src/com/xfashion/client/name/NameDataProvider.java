package com.xfashion.client.name;

import com.xfashion.client.FilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;

public class NameDataProvider extends FilterDataProvider<BrandDTO> {
	
	public Long getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getBrandId();
	}

}
