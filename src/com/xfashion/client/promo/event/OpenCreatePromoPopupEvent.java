package com.xfashion.client.promo.event;

import com.google.web.bindery.event.shared.Event;

public class OpenCreatePromoPopupEvent extends Event<OpenCreatePromoPopupHandler> {

	public static Type<OpenCreatePromoPopupHandler> TYPE = new Type<OpenCreatePromoPopupHandler>();
	
	public OpenCreatePromoPopupEvent() {
	}
	
	@Override
	public Type<OpenCreatePromoPopupHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(OpenCreatePromoPopupHandler handler) {
		handler.onOpenCreatePromoPopup(this);
	}

}
