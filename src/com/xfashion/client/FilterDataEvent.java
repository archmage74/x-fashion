package com.xfashion.client;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.FilterCellData;

public abstract class FilterDataEvent<H extends FilterDataEventHandler, T extends FilterCellData> extends Event<H> {

	T cellData;
	
	public FilterDataEvent(T cellData) {
		setCellData(cellData);
	}

	public T getCellData() {
		return cellData;
	}

	public void setCellData(T cellData) {
		this.cellData = cellData;
	}
	
}
