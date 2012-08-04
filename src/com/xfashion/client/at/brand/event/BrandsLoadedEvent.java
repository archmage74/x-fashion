package com.xfashion.client.at.brand.event;

import com.google.web.bindery.event.shared.Event;

public class BrandsLoadedEvent extends Event<BrandsLoadedHandler> {
	
	public static Type<BrandsLoadedHandler> TYPE = new Type<BrandsLoadedHandler>();

	@Override
	public Type<BrandsLoadedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BrandsLoadedHandler handler) {
		handler.onBrandsLoaded(this);
	}

}
