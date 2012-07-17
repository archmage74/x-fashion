package com.xfashion.client.color.event;

import com.google.web.bindery.event.shared.Event;

public class ClearColorSelectionEvent extends Event<ClearColorSelectionHandler> {

	public static Type<ClearColorSelectionHandler> TYPE = new Type<ClearColorSelectionHandler>();
	
	@Override
	public Type<ClearColorSelectionHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ClearColorSelectionHandler handler) {
		handler.onClearColorSelection(this);
	}

}
