package com.xfashion.client.at.category;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.SimpleFilterPanel;
import com.xfashion.client.at.category.event.SelectCategoryEvent;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.shared.CategoryDTO;

public class CategoryPanel extends SimpleFilterPanel<CategoryDTO> {

	public CategoryPanel(CategoryDataProvider dataProvider, EventBus eventBus) {
		super(dataProvider, eventBus);
		this.dataProvider = dataProvider; 
	}

	@Override
	public Panel createTablePanel() {
		VerticalPanel panel = new VerticalPanel();
		Panel headerPanel = createHeaderPanel(getPanelTitle());
		panel.add(headerPanel);

		cellTable = new CellTable<CategoryDTO>(20, GWT.<FilterTableResources> create(FilterTableResources.class));
		createColumns();

		cellTable.addHandler(createSelectHandler(), CellPreviewEvent.getType());
		cellTable.setStyleName("simpleFilterTable");
		dataProvider.addDataDisplay(cellTable);

		panel.add(cellTable);

		setCreateAnchor(new SimplePanel());
		panel.add(getCreateAnchor());

		return panel;
	}
	
	public void createColumns() {
		cellTable.addColumn(createNameColumn());
	}

	@Override
	public void select(CategoryDTO dto) {
		eventBus.fireEvent(new SelectCategoryEvent(dto));
	}

	@Override
	public void clearSelection() {
		eventBus.fireEvent(new SelectCategoryEvent(null));
	}

	@Override
	public FilterDataProvider<CategoryDTO> getDataProvider() {
		return dataProvider;
	}

	@Override
	public String getPanelTitle() {
		return textMessages.category();
	}

}
