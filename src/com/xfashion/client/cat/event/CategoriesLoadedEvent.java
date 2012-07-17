package com.xfashion.client.cat.event;

import com.google.web.bindery.event.shared.Event;

public class CategoriesLoadedEvent extends Event<CategoriesLoadedHandler> {
	
	public static Type<CategoriesLoadedHandler> TYPE = new Type<CategoriesLoadedHandler>();

	@Override
	public Type<CategoriesLoadedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CategoriesLoadedHandler handler) {
		handler.onCategoriesLoaded(this);
	}

}
