package com.xfashion.client;

import java.util.List;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.shared.FilterCellData;

public abstract class SimpleFilterPanel<T extends FilterCellData> extends FilterPanel<T> {

	protected ListDataProvider<T> dataProvider;
	protected CellTable<T> cellTable;

	public SimpleFilterPanel(ListDataProvider<T> dataProvider) {
		super();
		this.dataProvider = dataProvider;
	}
	
	@Override
	protected void handleSelection(CellPreviewEvent<T> event) {
		if (editMode && event.getColumn() > 0) {
			switch (event.getColumn()) {
			case 2:
				moveUp(event.getValue(), event.getIndex());
				break;
			case 3:
				moveDown(event.getValue(), event.getIndex());
				break;
			case 4:
				delete(event.getValue());
				break;
			}
		} else {
			event.getIndex();
			select(event.getValue());
		}
	}

	@Override
	public void hideTools() {
		removeAdditionalColumns();
		cellTable.addColumn(createNameColumn());
		cellTable.addColumn(createAmountColumn());
		redrawPanel();
		createAnchor.clear();
	}

	@Override
	public void showTools() {
		removeAdditionalColumns();
		cellTable.addColumn(createEditNameColumn());
		List<Column<T, ?>> toolColumns = createToolsColumns();
		for (Column<T, ?> c : toolColumns) {
			cellTable.addColumn(c);
		}
		redrawPanel();
		Widget create = createCreatePanel();
		createAnchor.add(create);
	}

	private void removeAdditionalColumns() {
		while (cellTable.getColumnCount() > 1) {
			cellTable.removeColumn(1);
		}
	}

	protected void redrawPanel() {
		cellTable.redraw();
	}

	public ListDataProvider<T> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(SimpleFilterDataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
	}

}
