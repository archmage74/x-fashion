package com.xfashion.client.pricechange.event;

import com.google.web.bindery.event.shared.Event;

public class PriceChangesUpdatedEvent extends Event<PriceChangesUpdatedHandler> {

	public static Type<PriceChangesUpdatedHandler> TYPE = new Type<PriceChangesUpdatedHandler>();
	
	private int priceChangesAmount;
	
	public PriceChangesUpdatedEvent(int priceChangesAmount) {
		this.priceChangesAmount = priceChangesAmount;
	}
	
	public int getPriceChangesAmount() {
		return priceChangesAmount;
	}

	@Override
	public Type<PriceChangesUpdatedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PriceChangesUpdatedHandler handler) {
		handler.onPriceChangesUpdated(this);
	}

}
