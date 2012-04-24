package com.xfashion.client.size;

import com.xfashion.client.FilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.SizeDTO;

public class SizeDataProvider extends FilterDataProvider<SizeDTO> {
	
	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getSize();
	}

}
