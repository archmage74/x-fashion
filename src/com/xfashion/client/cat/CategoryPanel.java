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
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.ICrud;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.ToolPanel;
import com.xfashion.client.resources.FilterListResources;
import com.xfashion.client.tool.Buttons;
import com.xfashion.shared.CategoryDTO;

public class CategoryPanel extends FilterPanel<CategoryDTO> implements ICrud<CategoryDTO> {

	private CategoryDTO selectedCategory;

	private VerticalPanel mainPanel;

	private CategoryCell cell;

	public CategoryPanel(PanelMediator panelMediator, FilterDataProvider<CategoryDTO> dataProvider) {
		super(panelMediator, dataProvider);
		panelMediator.setCategoryPanel(this);
	}

	public Panel createPanel() {
		mainPanel = new VerticalPanel();

		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		Label categoryLabel = new Label("Kategorie");
		categoryLabel.addStyleName("filterLabel categoryFilterLabel");
		headerPanel.add(categoryLabel);
		Image toolsButton = Buttons.showTools();
		//toolsButton.addClickHandler(new ClickHandler() {
		ClickHandler toolsButtonClickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				toggleTools();
			}
		};
		toolsButton.addClickHandler(toolsButtonClickHandler);
		headerPanel.add(toolsButton);

		mainPanel.add(headerPanel);
		setHeaderColor(null);

		cell = new CategoryCell(this, panelMediator);
		cellList = new CellList<CategoryDTO>(cell, GWT.<FilterListResources> create(FilterListResources.class));
		cellList.setPageSize(30);
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
					panelMediator.setHeaderColor(selectedCategory.getBorderColor());
					panelMediator.setSelectedCategory(selectedCategory);
				} else {
					selectedCategory = null;
					panelMediator.resetHeaderColor();
					panelMediator.setSelectedCategory(null);
				}
			}
		});
		getDataProvider().addDataDisplay(cellList);

		cellList.addStyleName("categoryList");
		mainPanel.add(cellList);

		setCreateAnchor(new SimplePanel());
		mainPanel.add(getCreateAnchor());

		return mainPanel;
	}
	
	@Override
	protected ToolPanel<CategoryDTO> createToolPanel() {
		ToolPanel<CategoryDTO> tp = new CategoryToolPanel(this, panelMediator);
		return tp;
	}
	
	public void delete(CategoryDTO category) {
		panelMediator.deleteCategory(category);
	}

	public void update(CategoryDTO category) {
		panelMediator.updateCategory(category);
		cellList.redraw();
	}

	public CategoryDTO getSelectedCategory() {
		return selectedCategory;
	}

	public PanelMediator getPanelMediator() {
		return panelMediator;
	}

//	class CategoryCell extends AbstractCell<CategoryDTO> {
//
//		public CategoryCell() {
//			super("change", "keydown");
//		}
//
//		@Override
//		public void onBrowserEvent(Context context, Element parent, CategoryDTO value, NativeEvent event, ValueUpdater<CategoryDTO> valueUpdater) {
//			if (value == null) {
//				return;
//			}
//			super.onBrowserEvent(context, parent, value, event, valueUpdater);
//			if ("change".equals(event.getType())) {
//				updateFromCell(parent, value);
//			}
//		}
//
//		@Override
//		public void onEnterKeyDown(Context context, Element parent, CategoryDTO value, NativeEvent event, ValueUpdater<CategoryDTO> valueUpdater) {
//			if (value == null) {
//				return;
//			}
//			updateFromCell(parent, value);
//		}
//
//		private void updateFromCell(Element parent, CategoryDTO value) {
//			if (value.isInEditMode()) {
//				value.setName(readCategoryName(parent));
//				value.setInEditMode(false);
//				update(value);
//			}
//		}
//
//		protected String readCategoryName(Element parent) {
//			InputElement e = parent.getFirstChild().getFirstChild().cast();
//			return e.getValue();
//		}
//
//		@Override
//		public void render(Context context, CategoryDTO category, SafeHtmlBuilder sb) {
//			if (category == null) {
//				return;
//			}
//
//			String css;
//			String style;
//			if (category.isSelected()) {
//				css = "categorySelected";
//				style = createSelectedStyle(category);
//			} else {
//				css = "categoryUnselected";
//				style = "";
//			}
//
//			sb.appendHtmlConstant("<div class=\"" + css + "\" style=\"" + style + "\">");
//			if (category.isInEditMode()) {
//				sb.appendHtmlConstant("<input type=\"text\" value=\"" + category.getName() + "\" style=\"height: 19px; border: 1px inset;\" />");
//			} else {
//				sb.appendHtmlConstant(category.getName());
//			}
//			sb.appendHtmlConstant("</div>");
//		}
//
//		private String createSelectedStyle(CategoryDTO cellData) {
//			CategoryDTO dto = cellData;
//			String style = "background-color: " + dto.getBackgroundColor() + "; " + "border: 2px solid " + dto.getBorderColor() + ";";
//			return style;
//		}
//	}
//	
	public void showCreatePopup() {

	}

	public void clearSelection() {

	}

}
