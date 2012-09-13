package com.xfashion.client.statistic.event;

import com.google.web.bindery.event.shared.Event;

public class ShowAllDetailsStatisticEvent extends Event<ShowAllDetailsStatisticHandler> {

	public static Type<ShowAllDetailsStatisticHandler> TYPE = new Type<ShowAllDetailsStatisticHandler>();
	
	public ShowAllDetailsStatisticEvent() {

	}
	
	@Override
	public Type<ShowAllDetailsStatisticHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(ShowAllDetailsStatisticHandler handler) {
		handler.onShowAllDetailsStatistic(this);
	}
	
}
