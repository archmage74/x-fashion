package com.xfashion.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.shared.FilterCellData2;

public abstract class SimpleFilterPanel<T extends FilterCellData2> extends FilterPanel2<T> {

	protected SimpleFilterDataProvider2<T> dataProvider;
	protected CellTable<T> cellTable;

	public SimpleFilterPanel(SimpleFilterDataProvider2<T> dataProvider) {
		super();
		this.dataProvider = dataProvider;
	}
	
	@Override
	public Panel createTablePanel() {
		VerticalPanel panel = new VerticalPanel();
		Panel headerPanel = createHeaderPanel(getPanelTitle());
		panel.add(headerPanel);

		cellTable = new CellTable<T>(35, GWT.<FilterTableResources> create(FilterTableResources.class));

		cellTable.addColumn(createIconColumn());
		cellTable.addColumn(createNameColumn());
		cellTable.addColumn(createAmountColumn());
		cellTable.addHandler(createSelectHandler(), CellPreviewEvent.getType());
		cellTable.setStyleName("simpleFilterTable");
		dataProvider.addDataDisplay(cellTable);

		panel.add(cellTable);

		setCreateAnchor(new SimplePanel());
		panel.add(getCreateAnchor());

		return panel;
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

	public SimpleFilterDataProvider2<T> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(SimpleFilterDataProvider2<T> dataProvider) {
		this.dataProvider = dataProvider;
	}

}
