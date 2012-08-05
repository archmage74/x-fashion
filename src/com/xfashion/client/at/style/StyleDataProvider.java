package com.xfashion.client.at.style;

import java.util.List;

import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.StyleDTO;

public class StyleDataProvider extends FilterDataProvider<StyleDTO> {

	public StyleDataProvider(ArticleTypeDataProvider articleProvider, EventBus eventBus) {
		super(articleProvider, eventBus);
	}

	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getColorKey();
	}

	@Override
	public List<ArticleTypeDTO> applyFilter(List<ArticleTypeDTO> articleTypes) {
		return null;
	}

}
