package com.xfashion.client.at.brand.event;

import com.google.web.bindery.event.shared.Event;

public class ShowChooseBrandPopupEvent extends Event<ShowChooseBrandPopupHandler> {

	public static Type<ShowChooseBrandPopupHandler> TYPE = new Type<ShowChooseBrandPopupHandler>();
	
	@Override
	public Type<ShowChooseBrandPopupHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowChooseBrandPopupHandler handler) {
		handler.onShowChooseBrandPopup(this);
	}

}
