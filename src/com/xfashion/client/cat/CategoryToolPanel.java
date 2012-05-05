package com.xfashion.client.cat;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.ErrorEvent;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.ToolPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.shared.CategoryDTO;

public class CategoryToolPanel extends ToolPanel<CategoryDTO> {

	protected ListBox createCategoryColorListBox;
	
	public CategoryToolPanel(FilterPanel<CategoryDTO> parentPanel, PanelMediator panelMediator) {
		super(parentPanel, panelMediator);
	}

	@Override
	protected void createDTOFromPanel() {
		CategoryDTO category = new CategoryDTO();
		String name = createTextBox.getText();
		if (name == null || name.length() == 0) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.createAttributeNoName()));
			return;
		}
		category.setName(name);
		String[] color = CategoryDTO.COLOR_SCHEMAS[createCategoryColorListBox.getSelectedIndex()];
		category.setBackgroundColor(color[0]);
		category.setBorderColor(color[1]);
		int numCats = parentPanel.getDataProvider().getList().size();
		int idx = 0;
		if (numCats != 0) {
			idx = parentPanel.getDataProvider().getList().get(numCats - 1).getSortIndex() + 1;
		}
		category.setSortIndex(idx);
		Xfashion.eventBus.fireEvent(new CreateCategoryEvent(category));
	}

	@Override
	protected Widget createCreatePanel() {
		Grid createGrid = new Grid(3, 1);

		createTextBox = new TextBox();
		createGrid.setWidget(0, 0, createTextBox);

		final ListBox createCategoryColorListBox = new ListBox();
		for (String[] color : CategoryDTO.COLOR_SCHEMAS) {
			createCategoryColorListBox.addItem(color[0] + " / " + color[1]);
		}
		createGrid.setWidget(1, 0, createCategoryColorListBox);

		Button addCategoryButton = new Button("Anlegen");
		addCategoryButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				CategoryDTO category = new CategoryDTO();
				String name = createTextBox.getText();
				if (name == null || name.length() == 0) {
					Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.createAttributeNoName()));
					return;
				}
				category.setName(name);
				String[] color = CategoryDTO.COLOR_SCHEMAS[createCategoryColorListBox.getSelectedIndex()];
				category.setBackgroundColor(color[0]);
				category.setBorderColor(color[1]);
				int numCats = parentPanel.getDataProvider().getList().size();
				int idx = 0;
				if (numCats != 0) {
					idx = parentPanel.getDataProvider().getList().get(numCats - 1).getSortIndex() + 1;
				}
				category.setSortIndex(idx);
				Xfashion.eventBus.fireEvent(new CreateCategoryEvent(category));
			}
		});
		createGrid.setWidget(2, 0, addCategoryButton);

		return createGrid;
	}

}
