package com.xfashion.client.statistic.event;

import com.google.web.bindery.event.shared.Event;

public class AddMoreSellStatisticEvent extends Event<AddMoreSellStatisticHandler> {

	public static Type<AddMoreSellStatisticHandler> TYPE = new Type<AddMoreSellStatisticHandler>();

	public AddMoreSellStatisticEvent() {
	}
	
	@Override
	public Type<AddMoreSellStatisticHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(AddMoreSellStatisticHandler handler) {
		handler.onAddMoreSellStatistic(this);
	}

}
