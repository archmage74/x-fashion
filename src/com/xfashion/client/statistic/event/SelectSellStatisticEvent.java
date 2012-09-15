package com.xfashion.client.statistic.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.statistic.SellStatisticDTO;

public class SelectSellStatisticEvent extends Event<SelectSellStatisticHandler> {

	public static Type<SelectSellStatisticHandler> TYPE = new Type<SelectSellStatisticHandler>();

	private SellStatisticDTO sellStatistic;
	
	public SelectSellStatisticEvent(SellStatisticDTO sellStatistic) {
		this.sellStatistic = sellStatistic;
	}
	
	public SellStatisticDTO getSellStatistic() {
		return sellStatistic;
	}
	
	@Override
	public Type<SelectSellStatisticHandler> getAssociatedType() {
		return TYPE;
	}
	
	@Override
	protected void dispatch(SelectSellStatisticHandler handler) {
		handler.onSelectSellStatistic(this);
	}

}
