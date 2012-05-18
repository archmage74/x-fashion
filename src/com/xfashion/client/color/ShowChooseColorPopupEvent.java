package com.xfashion.client.color;

import com.google.web.bindery.event.shared.Event;

public class ShowChooseColorPopupEvent extends Event<ShowChooseColorPopupHandler> {

	public static Type<ShowChooseColorPopupHandler> TYPE = new Type<ShowChooseColorPopupHandler>();
	
	@Override
	public Type<ShowChooseColorPopupHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowChooseColorPopupHandler handler) {
		handler.onShowChooseColorPopup(this);
	}

}
