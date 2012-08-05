package com.xfashion.client.at;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public abstract class ArticleDataProvider<T> extends ListDataProvider<T> {

	public abstract ArticleTypeDTO retrieveArticleType(T item);

	public abstract ArticleTypeDTO retrieveArticleType(Long productNumber);
	
	public Collection<String> getVisibleArticleNames() {
		Set<String> names = new HashSet<String>();
		for (T a : getList()) {
			ArticleTypeDTO articleType = retrieveArticleType(a);
			names.add(articleType.getName());
		}
		return names;
	}


}
