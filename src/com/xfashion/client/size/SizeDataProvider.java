package com.xfashion.client.size;

import com.xfashion.client.FilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public class SizeDataProvider extends FilterDataProvider<SizeCellData> {
	
	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getSize();
	}

}
