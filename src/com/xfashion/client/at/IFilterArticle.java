package com.xfashion.client.at;

import java.util.List;

import com.xfashion.shared.ArticleTypeDTO;

public interface IFilterArticle {

	public void applyFilters(List<ArticleTypeDTO> articles);
	
}
