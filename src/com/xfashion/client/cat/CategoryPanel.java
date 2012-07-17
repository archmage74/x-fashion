package com.xfashion.client.cat;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.SimpleFilterPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.cat.event.CreateCategoryEvent;
import com.xfashion.client.cat.event.DeleteCategoryEvent;
import com.xfashion.client.cat.event.MoveDownCategoryEvent;
import com.xfashion.client.cat.event.MoveUpCategoryEvent;
import com.xfashion.client.cat.event.SelectCategoryEvent;
import com.xfashion.client.cat.event.UpdateCategoryEvent;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.shared.CategoryDTO;

public class CategoryPanel extends SimpleFilterPanel<CategoryDTO> {

	private CategoryDataProvider dataProvider;
	protected CellTable<CategoryDTO> cellTable;

	public CategoryPanel(CategoryDataProvider dataProvider) {
		super(dataProvider);
		this.dataProvider = dataProvider; 
	}

	@Override
	public Panel createTablePanel() {
		VerticalPanel panel = new VerticalPanel();
		Panel headerPanel = createHeaderPanel(getPanelTitle());
		panel.add(headerPanel);

		cellTable = new CellTable<CategoryDTO>(20, GWT.<FilterTableResources> create(FilterTableResources.class));

		cellTable.addColumn(createNameColumn());
		cellTable.addHandler(createSelectHandler(), CellPreviewEvent.getType());
		cellTable.setStyleName("simpleFilterTable");
		dataProvider.addDataDisplay(cellTable);

		panel.add(cellTable);

		setCreateAnchor(new SimplePanel());
		panel.add(getCreateAnchor());

		return panel;
	}

	@Override
	protected void handleSelection(CellPreviewEvent<CategoryDTO> event) {
		if (editMode && event.getColumn() > 0) {
			switch (event.getColumn()) {
			case 1:
				moveUp(event.getValue(), event.getIndex());
				break;
			case 2:
				moveDown(event.getValue(), event.getIndex());
				break;
			case 3:
				delete(event.getValue());
				break;
			}
		} else {
			event.getIndex();
			select(event.getValue());
		}
	}

	@Override
	public void delete(CategoryDTO category) {
		Xfashion.eventBus.fireEvent(new DeleteCategoryEvent(category));
	}

	@Override
	public void clearSelection() {
		Xfashion.eventBus.fireEvent(new SelectCategoryEvent(null));
	}

	@Override
	public void hideTools() {
		removeAdditionalColumns();
		cellTable.addColumn(createNameColumn());
		redrawPanel();
		createAnchor.clear();
	}

	@Override
	public void showTools() {
		removeAdditionalColumns();
		cellTable.addColumn(createEditNameColumn());
		List<Column<CategoryDTO, ?>> toolColumns = createToolsColumns();
		for (Column<CategoryDTO, ?> c : toolColumns) {
			cellTable.addColumn(c);
		}
		redrawPanel();
		Widget create = createCreatePanel();
		createAnchor.add(create);
	}

	private void removeAdditionalColumns() {
		while (cellTable.getColumnCount() > 0) {
			cellTable.removeColumn(0);
		}
	}

	@Override
	public ListDataProvider<CategoryDTO> getDataProvider() {
		return dataProvider;
	}

	@Override
	public String getPanelTitle() {
		return textMessages.category();
	}

	@Override
	protected void moveUp(CategoryDTO dto, int index) {
		Xfashion.eventBus.fireEvent(new MoveUpCategoryEvent(dto, index));
	}

	@Override
	protected void moveDown(CategoryDTO dto, int index) {
		Xfashion.eventBus.fireEvent(new MoveDownCategoryEvent(dto, index));
	}

	@Override
	protected void select(CategoryDTO dto) {
		Xfashion.eventBus.fireEvent(new SelectCategoryEvent(dto));
	}

	@Override
	protected void updateDTO(CategoryDTO dto) {
		Xfashion.eventBus.fireEvent(new UpdateCategoryEvent(dto));
	}

	@Override
	protected void createDTO() {
		CategoryDTO size = new CategoryDTO();
		fillDTOFromPanel(size);
		Xfashion.eventBus.fireEvent(new CreateCategoryEvent(size));
	}

	@Override
	protected void redrawPanel() {
		cellTable.redraw();
	}
	
}
