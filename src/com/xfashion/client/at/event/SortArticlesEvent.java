package com.xfashion.client.at.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.client.at.sort.IArticleTypeComparator;

public class SortArticlesEvent extends Event<SortArticlesHandler> {
	
	public static Type<SortArticlesHandler> TYPE = new Type<SortArticlesHandler>();

	protected IArticleTypeComparator articleTypeComparator;
	
	public SortArticlesEvent(IArticleTypeComparator articleTypeComparator) {
		this.articleTypeComparator = articleTypeComparator;
	}
	
	@Override
	public Type<SortArticlesHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SortArticlesHandler handler) {
		handler.onSortArticles(this);
	}

	public IArticleTypeComparator getArticleTypeComparator() {
		return articleTypeComparator;
	}

}
