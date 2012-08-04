package com.xfashion.client.at.size.event;

import com.google.web.bindery.event.shared.Event;

public class SizesLoadedEvent extends Event<SizesLoadedHandler> {
	
	public static Type<SizesLoadedHandler> TYPE = new Type<SizesLoadedHandler>();

	@Override
	public Type<SizesLoadedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SizesLoadedHandler handler) {
		handler.onSizesLoaded(this);
	}

}
