package com.xfashion.client.stock.event;

import com.google.web.bindery.event.shared.Event;

public class RequestOpenSellPopupEvent extends Event<RequestOpenSellPopupHandler> {

	public static Type<RequestOpenSellPopupHandler> TYPE = new Type<RequestOpenSellPopupHandler>();
	
	@Override
	public Type<RequestOpenSellPopupHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RequestOpenSellPopupHandler handler) {
		handler.onRequestOpenSellPopup(this);
	}

}
