package com.xfashion.client.stock.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.UserDTO;

public class LoadStockEvent extends Event<LoadStockHandler> {

	public static Type<LoadStockHandler> TYPE = new Type<LoadStockHandler>();
	
	private UserDTO user;
	
	public LoadStockEvent(UserDTO user) {
		this.user = user;
	}
	
	public UserDTO getUser() {
		return user;
	}
	
	@Override
	public Type<LoadStockHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(LoadStockHandler handler) {
		handler.onLoadStock(this);
	}
	
}
