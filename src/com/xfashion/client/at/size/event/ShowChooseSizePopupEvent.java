package com.xfashion.client.at.size.event;

import com.google.web.bindery.event.shared.Event;

public class ShowChooseSizePopupEvent extends Event<ShowChooseSizePopupHandler> {

	public static Type<ShowChooseSizePopupHandler> TYPE = new Type<ShowChooseSizePopupHandler>();
	
	@Override
	public Type<ShowChooseSizePopupHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowChooseSizePopupHandler handler) {
		handler.onShowChooseSizePopup(this);
	}

}
