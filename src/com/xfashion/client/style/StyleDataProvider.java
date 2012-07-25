package com.xfashion.client.style;

import java.util.List;

import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.StyleDTO;

public class StyleDataProvider extends FilterDataProvider<StyleDTO> {

	public StyleDataProvider(ArticleTypeDataProvider articleProvider) {
		super(articleProvider);
	}

	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getColorKey();
	}

	@Override
	public List<ArticleTypeDTO> applyFilter(List<ArticleTypeDTO> articleTypes) {
		return null;
	}

}
