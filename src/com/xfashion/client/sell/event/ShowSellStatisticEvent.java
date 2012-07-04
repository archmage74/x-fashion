package com.xfashion.client.sell.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ShopDTO;

public class ShowSellStatisticEvent extends Event<ShowSellStatisticHandler> {

	public static Type<ShowSellStatisticHandler> TYPE = new Type<ShowSellStatisticHandler>();
	
	protected ShopDTO shop;
	
	public ShowSellStatisticEvent(ShopDTO shop) {
		this.shop = shop;
	}
	
	public ShopDTO getShop() {
		return shop;
	}
	
	@Override
	public Type<ShowSellStatisticHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowSellStatisticHandler handler) {
		handler.onSellFromStock(this);
	}

}
