package com.xfashion.client.notepad.event;

import com.google.web.bindery.event.shared.Event;

public class RequestIntoStockEvent extends Event<RequestIntoStockHandler> {

	public static Type<RequestIntoStockHandler> TYPE = new Type<RequestIntoStockHandler>();
	
	@Override
	public Type<RequestIntoStockHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RequestIntoStockHandler handler) {
		handler.onRequestIntoStock(this);
	}

}
