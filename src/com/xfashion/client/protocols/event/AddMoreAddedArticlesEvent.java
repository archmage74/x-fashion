package com.xfashion.client.protocols.event;

import com.google.web.bindery.event.shared.Event;

public class AddMoreAddedArticlesEvent extends Event<AddMoreAddedArticlesHandler> {

	public static Type<AddMoreAddedArticlesHandler> TYPE = new Type<AddMoreAddedArticlesHandler>();
	
	public AddMoreAddedArticlesEvent() {
		super();
	}
	
	@Override
	public Type<AddMoreAddedArticlesHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddMoreAddedArticlesHandler handler) {
		handler.onAddMoreAddedArticles(this);
	}

}
