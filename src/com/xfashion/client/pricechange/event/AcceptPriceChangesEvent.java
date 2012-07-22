package com.xfashion.client.pricechange.event;

import com.google.web.bindery.event.shared.Event;

public class AcceptPriceChangesEvent extends Event<AcceptPriceChangesHandler> {

	public static Type<AcceptPriceChangesHandler> TYPE = new Type<AcceptPriceChangesHandler>();
	
	@Override
	public Type<AcceptPriceChangesHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AcceptPriceChangesHandler handler) {
		handler.onAcceptPriceChanges(this);
	}

}
