package com.xfashion.client.at;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public abstract class ArticleDataProvider<T> extends ListDataProvider<T> {

	public abstract ArticleTypeDTO retrieveArticleType(T item);

}
