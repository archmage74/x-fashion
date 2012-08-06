package com.xfashion.client.at.event;

import com.google.web.bindery.event.shared.Event;

public class MinimizeAllFilterPanelsEvent extends Event<MinimizeAllFilterPanelsHandler> {

	public static Type<MinimizeAllFilterPanelsHandler> TYPE = new Type<MinimizeAllFilterPanelsHandler>();
	
	@Override
	public Type<MinimizeAllFilterPanelsHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MinimizeAllFilterPanelsHandler handler) {
		handler.onMinimizeAllFilterPanels(this);
	}

}
