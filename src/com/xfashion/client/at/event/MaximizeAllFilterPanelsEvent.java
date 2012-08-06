package com.xfashion.client.at.event;

import com.google.web.bindery.event.shared.Event;

public class MaximizeAllFilterPanelsEvent extends Event<MaximizeAllFilterPanelsHandler> {

	public static Type<MaximizeAllFilterPanelsHandler> TYPE = new Type<MaximizeAllFilterPanelsHandler>();
	
	@Override
	public Type<MaximizeAllFilterPanelsHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MaximizeAllFilterPanelsHandler handler) {
		handler.onMaximizeAllFilterPanels(this);
	}

}
