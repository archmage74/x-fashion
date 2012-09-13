package com.xfashion.client.statistic.event;

import com.google.web.bindery.event.shared.Event;

public class ShowSizesDetailStatisticEvent extends Event<ShowSizesDetailStatisticHandler> {

	public static Type<ShowSizesDetailStatisticHandler> TYPE = new Type<ShowSizesDetailStatisticHandler>();
	
	public ShowSizesDetailStatisticEvent() {

	}
	
	@Override
	public Type<ShowSizesDetailStatisticHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(ShowSizesDetailStatisticHandler handler) {
		handler.onShowSizesDetailStatistic(this);
	}
	
}
