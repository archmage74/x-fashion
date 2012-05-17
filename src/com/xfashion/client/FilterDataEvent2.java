package com.xfashion.client;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.FilterCellData2;

public abstract class FilterDataEvent2<H extends FilterDataEventHandler, T extends FilterCellData2> extends Event<H> {

	T cellData;
	
	public FilterDataEvent2(T cellData) {
		setCellData(cellData);
	}

	public T getCellData() {
		return cellData;
	}

	public void setCellData(T cellData) {
		this.cellData = cellData;
	}
	
}
