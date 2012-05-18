package com.xfashion.client.style;

import com.google.web.bindery.event.shared.Event;

public class ClearStyleSelectionEvent extends Event<ClearStyleSelectionHandler> {

	public static Type<ClearStyleSelectionHandler> TYPE = new Type<ClearStyleSelectionHandler>();
	
	@Override
	public Type<ClearStyleSelectionHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ClearStyleSelectionHandler handler) {
		handler.onClearStyleSelection(this);
	}

}
