package com.xfashion.client.removed.event;

import com.google.web.bindery.event.shared.Event;

public class AddMoreRemovedArticlesEvent extends Event<AddMoreRemovedArticlesHandler> {

	public static Type<AddMoreRemovedArticlesHandler> TYPE = new Type<AddMoreRemovedArticlesHandler>();
	
	public AddMoreRemovedArticlesEvent() {
		super();
	}
	
	@Override
	public Type<AddMoreRemovedArticlesHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddMoreRemovedArticlesHandler handler) {
		handler.onAddMoreRemovedArticles(this);
	}

}
