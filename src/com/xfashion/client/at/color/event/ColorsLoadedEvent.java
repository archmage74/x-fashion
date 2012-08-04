package com.xfashion.client.at.color.event;

import com.google.web.bindery.event.shared.Event;

public class ColorsLoadedEvent extends Event<ColorsLoadedHandler> {
	
	public static Type<ColorsLoadedHandler> TYPE = new Type<ColorsLoadedHandler>();

	@Override
	public Type<ColorsLoadedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ColorsLoadedHandler handler) {
		handler.onColorsLoaded(this);
	}

}
