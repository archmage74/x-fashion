package com.xfashion.client.statistic.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.ShopDTO;

public class SelectShopSellStatisticEvent extends Event<SelectShopSellStatisticHandler> {

	public static Type<SelectShopSellStatisticHandler> TYPE = new Type<SelectShopSellStatisticHandler>();

	private ShopDTO shop;
	
	public SelectShopSellStatisticEvent(ShopDTO shop) {
		this.shop = shop;
	}
	
	public ShopDTO getShop() {
		return shop;
	}
	
	@Override
	public Type<SelectShopSellStatisticHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(SelectShopSellStatisticHandler handler) {
		handler.onSelectShopSellStatistic(this);
	}

}
