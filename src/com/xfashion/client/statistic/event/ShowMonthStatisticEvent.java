package com.xfashion.client.statistic.event;

import com.google.web.bindery.event.shared.Event;

public class ShowMonthStatisticEvent extends Event<ShowMonthStatisticHandler> {

	public static Type<ShowMonthStatisticHandler> TYPE = new Type<ShowMonthStatisticHandler>();
	
	public ShowMonthStatisticEvent() {

	}
	
	@Override
	public Type<ShowMonthStatisticHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(ShowMonthStatisticHandler handler) {
		handler.onShowMonthStatistic(this);
	}
	
}
