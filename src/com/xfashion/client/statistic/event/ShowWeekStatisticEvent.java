package com.xfashion.client.statistic.event;

import com.google.web.bindery.event.shared.Event;

public class ShowWeekStatisticEvent extends Event<ShowWeekStatisticHandler> {

	public static Type<ShowWeekStatisticHandler> TYPE = new Type<ShowWeekStatisticHandler>();
	
	public ShowWeekStatisticEvent() {

	}
	
	@Override
	public Type<ShowWeekStatisticHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(ShowWeekStatisticHandler handler) {
		handler.onShowWeekStatistic(this);
	}
	
}
