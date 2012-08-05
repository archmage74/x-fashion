package com.xfashion.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.shared.FilterCellData;

public abstract class SimpleFilterPanel<T extends FilterCellData> extends FilterPanel<T> {

	protected FilterDataProvider<T> dataProvider;
	protected CellTable<T> cellTable;

	public SimpleFilterPanel(FilterDataProvider<T> dataProvider, EventBus eventBus) {
		super(eventBus);
		this.dataProvider = dataProvider;
	}
	
	public CellTable<T> getCellTable() {
		return cellTable;
	}

	public FilterDataProvider<T> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(SimpleFilterDataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
	}

	public void redrawPanel() {
		cellTable.redraw();
	}
	
	public void createColumns() {
		cellTable.addColumn(createNameColumn());
		cellTable.addColumn(createAmountColumn());
	}

}
