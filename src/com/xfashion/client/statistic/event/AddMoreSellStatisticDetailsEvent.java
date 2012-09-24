package com.xfashion.client.statistic.event;

import com.google.web.bindery.event.shared.Event;

public class AddMoreSellStatisticDetailsEvent extends Event<AddMoreSellStatisticDetailsHandler> {

	public static Type<AddMoreSellStatisticDetailsHandler> TYPE = new Type<AddMoreSellStatisticDetailsHandler>();

	public AddMoreSellStatisticDetailsEvent() {
	}
	
	@Override
	public Type<AddMoreSellStatisticDetailsHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(AddMoreSellStatisticDetailsHandler handler) {
		handler.onAddMoreSellStatisticDetails(this);
	}

}
