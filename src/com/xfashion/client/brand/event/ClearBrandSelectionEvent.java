package com.xfashion.client.brand.event;

import com.google.web.bindery.event.shared.Event;

public class ClearBrandSelectionEvent extends Event<ClearBrandSelectionHandler> {

	public static Type<ClearBrandSelectionHandler> TYPE = new Type<ClearBrandSelectionHandler>();
	
	@Override
	public Type<ClearBrandSelectionHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ClearBrandSelectionHandler handler) {
		handler.onClearBrandSelection(this);
	}

}
