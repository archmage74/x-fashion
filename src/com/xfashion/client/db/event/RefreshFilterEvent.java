package com.xfashion.client.db.event;

import com.google.web.bindery.event.shared.Event;

public class RefreshFilterEvent extends Event<RefreshFilterHandler> {
	
	public static Type<RefreshFilterHandler> TYPE = new Type<RefreshFilterHandler>();

	@Override
	public Type<RefreshFilterHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RefreshFilterHandler handler) {
		handler.onRefreshFilter(this);
	}

}
