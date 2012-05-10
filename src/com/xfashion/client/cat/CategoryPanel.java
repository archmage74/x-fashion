package com.xfashion.client.cat;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.ICrud;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.ToolPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.FilterListResources;
import com.xfashion.client.tool.Buttons;
import com.xfashion.shared.CategoryDTO;

public class CategoryPanel extends FilterPanel<CategoryDTO> implements ICrud<CategoryDTO> {

	private CategoryDTO selectedCategory;

	private CategoryCell cell;

	public CategoryPanel(PanelMediator panelMediator, CategoryDataProvider dataProvider) {
		super(panelMediator, dataProvider);
		panelMediator.setCategoryPanel(this);
	}

	public Panel createListPanel() {
		listPanel = new VerticalPanel();

		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		Label categoryLabel = new Label(textMessages.category());
		categoryLabel.addStyleName("filterLabel categoryFilterLabel");
		headerPanel.add(categoryLabel);
		Image toolsButton = Buttons.showTools();
		ClickHandler toolsButtonClickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				toggleTools();
			}
		};
		toolsButton.addClickHandler(toolsButtonClickHandler);
		headerPanel.add(toolsButton);

		listPanel.add(headerPanel);

		cell = new CategoryCell(this, panelMediator);
		cellList = new CellList<CategoryDTO>(cell, GWT.<FilterListResources> create(FilterListResources.class));
		cellList.setPageSize(20);
		cellList.addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
			@Override
			public void onRowCountChange(RowCountChangeEvent event) {
				if (toolPanel != null) {
					refreshToolsPanel();
				}
			}
		});

		final SingleSelectionModel<CategoryDTO> selectionModel = new SingleSelectionModel<CategoryDTO>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				if (selectedCategory != null) {
					selectedCategory.setSelected(false);
				}
				selectedCategory = selectionModel.getSelectedObject();
				if (selectionModel.getSelectedObject() != null) {
					selectedCategory.setSelected(true);
					panelMediator.setSelectedCategory(selectedCategory);
				} else {
					selectedCategory = null;
					panelMediator.setSelectedCategory(null);
				}
			}
		});
		getDataProvider().addDataDisplay(cellList);

		cellList.addStyleName("categoryList");
		listPanel.add(cellList);

		setCreateAnchor(new SimplePanel());
		listPanel.add(getCreateAnchor());
		
		return listPanel;
	}
	
	@Override
	protected ToolPanel<CategoryDTO> createToolPanel() {
		ToolPanel<CategoryDTO> tp = new CategoryToolPanel(this, panelMediator);
		return tp;
	}
	
	public void delete(CategoryDTO category) {
		Xfashion.eventBus.fireEvent(new DeleteCategoryEvent(category));
	}

	public void update(CategoryDTO category) {
		Xfashion.eventBus.fireEvent(new UpdateCategoryEvent(category));
		cellList.redraw();
	}

	public CategoryDTO getSelectedCategory() {
		return selectedCategory;
	}

	public PanelMediator getPanelMediator() {
		return panelMediator;
	}

	public void showCreatePopup() {

	}

	public void clearSelection() {

	}

}
