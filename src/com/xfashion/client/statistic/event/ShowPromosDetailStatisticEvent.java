package com.xfashion.client.statistic.event;

import com.google.web.bindery.event.shared.Event;

public class ShowPromosDetailStatisticEvent extends Event<ShowPromosDetailStatisticHandler> {

	public static Type<ShowPromosDetailStatisticHandler> TYPE = new Type<ShowPromosDetailStatisticHandler>();
	
	public ShowPromosDetailStatisticEvent() {

	}
	
	@Override
	public Type<ShowPromosDetailStatisticHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(ShowPromosDetailStatisticHandler handler) {
		handler.onShowPromosDetailStatistic(this);
	}
	
}
