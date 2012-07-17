package com.xfashion.client.size.event;

import com.google.web.bindery.event.shared.Event;

public class ClearSizeSelectionEvent extends Event<ClearSizeSelectionHandler> {

	public static Type<ClearSizeSelectionHandler> TYPE = new Type<ClearSizeSelectionHandler>();
	
	@Override
	public Type<ClearSizeSelectionHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ClearSizeSelectionHandler handler) {
		handler.onClearSizeSelection(this);
	}

}
