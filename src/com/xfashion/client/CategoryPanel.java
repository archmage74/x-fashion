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

public class CategoryPanel {
	
	private CategoryDTO selectedCategory;
	
	private CategoryCell cell;
	
	private Panel categoryHeaderPanel; 

	private String headerStyle;
	
	private PanelMediator panelMediator;
	
	public CategoryPanel(PanelMediator panelMediator) {
		this.panelMediator = panelMediator;
		panelMediator.setCategoryPanel(this);
	}
	
	public void setHeaderStyle(String style) {
		if (headerStyle != null) {
			categoryHeaderPanel.removeStyleName(headerStyle);
		}
		headerStyle = style;
		if (headerStyle != null) {
			categoryHeaderPanel.addStyleName(headerStyle);
		}
	}

	public Panel createPanel(ListDataProvider<CategoryDTO> categoryProvider) {
		VerticalPanel panel = new VerticalPanel();

		categoryHeaderPanel = new HorizontalPanel();
		categoryHeaderPanel.addStyleName("filterHeader");
		Label categoryLabel = new Label("Kategorie");
		categoryLabel.addStyleName("filterLabel");
		categoryHeaderPanel.add(categoryLabel);
		panel.add(categoryHeaderPanel);

		cell = new CategoryCell();
		final CellList<CategoryDTO> categoryList = new CellList<CategoryDTO>(cell, GWT.<StyleListResources> create(StyleListResources.class));
		categoryList.setPageSize(30);

		final SingleSelectionModel<CategoryDTO> selectionModel = new SingleSelectionModel<CategoryDTO>();
		categoryList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedCategory = selectionModel.getSelectedObject();
				if (selectionModel.getSelectedObject() != null) {
					String selectedCategoryName = selectionModel.getSelectedObject().getName();
					if (selectedCategoryName.equals("Damenhose")) {
						panelMediator.setHeaderStyle("categoryFemaleTrousers");
					} else if (selectedCategoryName.equals("Herrenhose")) {
						panelMediator.setHeaderStyle("categoryMaleTrousers");
					} else if (selectedCategoryName.equals("Damenoberteil")) {
						panelMediator.setHeaderStyle("categoryFemaleTop");
					} else if (selectedCategoryName.equals("Herrenoberteil")) {
						panelMediator.setHeaderStyle("categoryMaleTop");
					} else if (selectedCategoryName.equals("Kleider")) {
						panelMediator.setHeaderStyle("categorySkirt");
					} else if (selectedCategoryName.equals("Strumpfwaren")) {
						panelMediator.setHeaderStyle("categoryStockings");
					} else if (selectedCategoryName.equals("Gürtel")) {
						panelMediator.setHeaderStyle("categoryBelt");
					} else if (selectedCategoryName.equals("Bademode")) {
						panelMediator.setHeaderStyle("categoryBathing");
					} else if (selectedCategoryName.equals("Accessoirs")) {
						panelMediator.setHeaderStyle("categoryAccessories");
					}
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
			// System.out.println("sel: " + selectedCategory);
			String categoryId = "categoryUnselected";
			String categoryName = category.getName();
			if (categoryName.equals("Herrenhose") && category.equals(selectedCategory)) {
				categoryId = "categoryMaleTrousersSelected";
			} else if (categoryName.equals("Damenhose") && category.equals(selectedCategory)) {
				categoryId = "categoryFemaleTrousersSelected";
			} else if (categoryName.equals("Herrenoberteil") && category.equals(selectedCategory)) {
				categoryId = "categoryMaleTopSelected";
			} else if (categoryName.equals("Damenoberteil") && category.equals(selectedCategory)) {
				categoryId = "categoryFemaleTopSelected";
			} else if (categoryName.equals("Kleider") && category.equals(selectedCategory)) {
				categoryId = "categorySkirtSelected";
			} else if (categoryName.equals("Strumpfwaren") && category.equals(selectedCategory)) {
				categoryId = "categoryStockingsSelected";
			} else if (categoryName.equals("Gürtel") && category.equals(selectedCategory)) {
				categoryId = "categoryBeltSelected";
			} else if (categoryName.equals("Bademode") && category.equals(selectedCategory)) {
				categoryId = "categoryBathingSelected";
			} else if (categoryName.equals("Accessoirs") && category.equals(selectedCategory)) {
				categoryId = "categoryAccessoriesSelected";
			}

			sb.appendHtmlConstant("<div id=\"" + categoryId + "\">");
//			sb.appendHtmlConstant("<div class=\"" + categoryClass + "\"style=\"color: " + color + " height:32px; margin-left:3px; margin-right:3px; font-size:20px; \">");
			sb.appendHtmlConstant(category.getName());
			sb.appendHtmlConstant("</div>");
		}
	}

}
