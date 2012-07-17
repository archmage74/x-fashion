package com.xfashion.client.db.event;

import com.google.web.bindery.event.shared.Event;

public class ArticlesLoadedEvent extends Event<ArticlesLoadedHandler> {
	
	public static Type<ArticlesLoadedHandler> TYPE = new Type<ArticlesLoadedHandler>();

	@Override
	public Type<ArticlesLoadedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ArticlesLoadedHandler handler) {
		handler.onArticlesLoaded(this);
	}

}
