package com.xfashion.client.statistic.event;

import com.google.web.bindery.event.shared.Event;

public class ShowCategoriesDetailStatisticEvent extends Event<ShowCategoriesDetailStatisticHandler> {

	public static Type<ShowCategoriesDetailStatisticHandler> TYPE = new Type<ShowCategoriesDetailStatisticHandler>();
	
	public ShowCategoriesDetailStatisticEvent() {

	}
	
	@Override
	public Type<ShowCategoriesDetailStatisticHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(ShowCategoriesDetailStatisticHandler handler) {
		handler.onShowCategoriesDetailStatistic(this);
	}
	
}
