package com.xfashion.client.stock.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.client.stock.StockDataProvider;

public class StockLoadedEvent extends Event<StockLoadedHandler> {

	public static Type<StockLoadedHandler> TYPE = new Type<StockLoadedHandler>();

	private StockDataProvider stockProvider;
	
	public StockLoadedEvent(StockDataProvider stockProvider) {
		this.stockProvider = stockProvider;
	}

	public StockDataProvider getStockProvider() {
		return stockProvider;
	}

	@Override
	public Type<StockLoadedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(StockLoadedHandler handler) {
		handler.onStockLoaded(this);
	}
}
