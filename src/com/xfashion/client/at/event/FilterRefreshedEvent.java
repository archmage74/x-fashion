package com.xfashion.client.at.event;

import com.google.web.bindery.event.shared.Event;

public class FilterRefreshedEvent extends Event<FilterRefreshedHandler> {
	
	public static Type<FilterRefreshedHandler> TYPE = new Type<FilterRefreshedHandler>();

	@Override
	public Type<FilterRefreshedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FilterRefreshedHandler handler) {
		handler.onFilterRefreshed(this);
	}

}
