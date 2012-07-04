package com.xfashion.client.sell.event;

import com.google.web.bindery.event.shared.Event;

public class AddMoreSoldArticlesEvent extends Event<AddMoreSoldArticlesHandler> {

	public static Type<AddMoreSoldArticlesHandler> TYPE = new Type<AddMoreSoldArticlesHandler>();
	
	public AddMoreSoldArticlesEvent() {
		super();
	}
	
	@Override
	public Type<AddMoreSoldArticlesHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddMoreSoldArticlesHandler handler) {
		handler.onAddMoreSoldArticles(this);
	}

}
