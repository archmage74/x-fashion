package com.xfashion.client.at;

import com.google.web.bindery.event.shared.Event;

public class ArticlesDatabaseLoadedEvent extends Event<ArticleDatabaseLoadedHandler> {

	public static Type<ArticleDatabaseLoadedHandler> TYPE = new Type<ArticleDatabaseLoadedHandler>();
	
	public ArticlesDatabaseLoadedEvent() {
	}
	
	@Override
	public Type<ArticleDatabaseLoadedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ArticleDatabaseLoadedHandler handler) {
		handler.onArticleDatabaseLoaded(this);
	}

}
