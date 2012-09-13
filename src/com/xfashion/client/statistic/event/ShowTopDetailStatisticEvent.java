package com.xfashion.client.statistic.event;

import com.google.web.bindery.event.shared.Event;

public class ShowTopDetailStatisticEvent extends Event<ShowTopDetailStatisticHandler> {

	public static Type<ShowTopDetailStatisticHandler> TYPE = new Type<ShowTopDetailStatisticHandler>();
	
	public ShowTopDetailStatisticEvent() {

	}
	
	@Override
	public Type<ShowTopDetailStatisticHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(ShowTopDetailStatisticHandler handler) {
		handler.onShowTopDetailStatistic(this);
	}
	
}
