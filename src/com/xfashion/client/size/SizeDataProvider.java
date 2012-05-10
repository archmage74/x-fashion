package com.xfashion.client.size;

import com.xfashion.client.SimpleFilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.SizeDTO;

public class SizeDataProvider extends SimpleFilterDataProvider<SizeDTO> {
	
	public Long getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getSizeId();
	}

}
