package com.xfashion.client.statistic.event;

import com.google.web.bindery.event.shared.Event;

public class ShowDayStatisticEvent extends Event<ShowDayStatisticHandler> {

	public static Type<ShowDayStatisticHandler> TYPE = new Type<ShowDayStatisticHandler>();
	
	public ShowDayStatisticEvent() {

	}
	
	@Override
	public Type<ShowDayStatisticHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(ShowDayStatisticHandler handler) {
		handler.onShowDayStatistic(this);
	}
	
}
