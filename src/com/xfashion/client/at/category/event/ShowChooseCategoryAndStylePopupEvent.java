package com.xfashion.client.at.category.event;

import com.google.web.bindery.event.shared.Event;

public class ShowChooseCategoryAndStylePopupEvent extends Event<ShowChooseCategoryAndStylePopupHandler> {

	public static Type<ShowChooseCategoryAndStylePopupHandler> TYPE = new Type<ShowChooseCategoryAndStylePopupHandler>();
	
	@Override
	public Type<ShowChooseCategoryAndStylePopupHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowChooseCategoryAndStylePopupHandler handler) {
		handler.onShowChooseCategoryAndStylePopup(this);
	}

}
