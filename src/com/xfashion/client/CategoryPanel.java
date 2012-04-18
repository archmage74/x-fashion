package com.xfashion.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.xfashion.shared.CategoryDTO;

public class CategoryPanel extends FilterPanel {
	
	private CategoryDTO selectedCategory;
	
	private CategoryCell cell;
	
	public CategoryPanel(PanelMediator panelMediator) {
		super(panelMediator);
		panelMediator.setCategoryPanel(this);
	}
	
	public Panel createPanel(ListDataProvider<CategoryDTO> categoryProvider) {
		VerticalPanel panel = new VerticalPanel();

		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		Label categoryLabel = new Label("Kategorie");
		categoryLabel.addStyleName("filterLabel");
		headerPanel.add(categoryLabel);
		panel.add(headerPanel);
		setHeaderColor(null);
		
		cell = new CategoryCell();
		final CellList<CategoryDTO> categoryList = new CellList<CategoryDTO>(cell, GWT.<StyleListResources> create(StyleListResources.class));
		categoryList.setPageSize(30);

		final SingleSelectionModel<CategoryDTO> selectionModel = new SingleSelectionModel<CategoryDTO>();
		categoryList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedCategory = selectionModel.getSelectedObject();
				if (selectionModel.getSelectedObject() != null) {
					CategoryDTO selectedCategory = selectionModel.getSelectedObject();
					panelMediator.setHeaderColor(selectedCategory.getBorderColor());
				}
				panelMediator.setSelectedCategory(selectionModel.getSelectedObject());
			}
		});
		categoryProvider.addDataDisplay(categoryList);

		categoryList.addStyleName("categoryList");
		panel.add(categoryList);

		return panel;
	}

	public CategoryDTO getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(CategoryDTO selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
	
	public PanelMediator getPanelMediator() {
		return panelMediator;
	}

	class CategoryCell extends AbstractCell<CategoryDTO> {
		@Override
		public void render(Context context, CategoryDTO category, SafeHtmlBuilder sb) {
			if (category == null) {
				return;
			}

			String css;
			String style;
			if (category.equals(selectedCategory)) {
				css = "categorySelected";
				style = createSelectedStyle(category);
			} else {
				css = "categoryUnselected";
				style = "";
			}

			sb.appendHtmlConstant("<div class=\"" + css + "\" style=\"" + style + "\">");
			sb.appendHtmlConstant(category.getName());
			sb.appendHtmlConstant("</div>");
		}
		
		private String createSelectedStyle(CategoryDTO dto) {
			String style = "background-color: " + dto.getBackgroundColor() + "; " +
				"border: 2px solid " + dto.getBorderColor() + ";";
			return style;
		}
	}
	
	public void showCreatePopup() {
		
	}

	public void clearSelection() {
	
	}

}
