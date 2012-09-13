package com.xfashion.client.statistic.event;

import com.google.web.bindery.event.shared.Event;

public class ShowYearStatisticEvent extends Event<ShowYearStatisticHandler> {

	public static Type<ShowYearStatisticHandler> TYPE = new Type<ShowYearStatisticHandler>();
	
	public ShowYearStatisticEvent() {

	}
	
	@Override
	public Type<ShowYearStatisticHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(ShowYearStatisticHandler handler) {
		handler.onShowYearStatistic(this);
	}
	
}
