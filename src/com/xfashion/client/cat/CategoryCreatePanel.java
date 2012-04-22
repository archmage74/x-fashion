package com.xfashion.client.cat;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.shared.CategoryDTO;

public class CategoryCreatePanel {

	private CategoryPanel categoryPanel;
	
	private PanelMediator panelMediator;
	
	private Grid createCategoryGrid;
	
	private TextBox createCategoryTextBox;
	
	private ErrorMessages errorMessages;
	
	public CategoryCreatePanel(CategoryPanel categoryPanel, PanelMediator panelMediator) {
		errorMessages = GWT.create(ErrorMessages.class);
		this.categoryPanel = categoryPanel;
		this.panelMediator = panelMediator;
	}
	
	public Widget show() {
		createCategoryGrid = new Grid(3, 1);

		createCategoryTextBox = new TextBox();
		createCategoryGrid.setWidget(0, 0, createCategoryTextBox);

		final ListBox createCategoryColorListBox = new ListBox();
		for (String[] color : CategoryDTO.COLOR_SCHEMAS) {
			createCategoryColorListBox.addItem(color[0] + " / " + color[1]);
		}
		createCategoryGrid.setWidget(1, 0, createCategoryColorListBox);

		Button addCategoryButton = new Button("Anlegen");
		addCategoryButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				CategoryDTO category = new CategoryDTO();
				String name = createCategoryTextBox.getText();
				if (name == null || name.length() == 0) {
					panelMediator.showError(errorMessages.categoryCreateNoName());
					return;
				}
				category.setName(name);
				String[] color = CategoryDTO.COLOR_SCHEMAS[createCategoryColorListBox.getSelectedIndex()];
				category.setBackgroundColor(color[0]);
				category.setBorderColor(color[1]);
				int numCats = categoryPanel.getCategoryProvider().getList().size();
				int idx = 0;
				if (numCats != 0) {
					idx = categoryPanel.getCategoryProvider().getList().get(numCats - 1).getCategoryDTO().getSortIndex() + 1;
				}
				category.setSortIndex(idx);
				panelMediator.createCategory(category);
			}
		});
		createCategoryGrid.setWidget(2, 0, addCategoryButton);

		return createCategoryGrid;
	}
	
	public void hide() {
		createCategoryTextBox = null;
		createCategoryGrid.clear();
		createCategoryGrid = null;
	}

}
