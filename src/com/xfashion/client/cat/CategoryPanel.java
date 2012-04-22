package com.xfashion.client.cat;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.ICrud;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.resources.FilterListResources;
import com.xfashion.client.tool.Buttons;
import com.xfashion.shared.CategoryDTO;

public class CategoryPanel extends FilterPanel<CategoryCellData> implements ICrud<CategoryCellData> {

	private CategoryCellData selectedCategory;

	private CellList<CategoryCellData> categoryList;

	private VerticalPanel mainPanel;

	private SimplePanel createAnchor;

	private CategoryCell cell;

	private Image toolsButton;

	private CategoryToolPanel toolPanel;
	
	private CategoryCreatePanel createPanel;

	private FilterDataProvider<CategoryCellData> categoryProvider;

	// private ErrorMessages errorMessages;

	public CategoryPanel(PanelMediator panelMediator) {
		super(panelMediator);
		// errorMessages = GWT.create(ErrorMessages.class);
		panelMediator.setCategoryPanel(this);
	}

	public Panel createPanel(FilterDataProvider<CategoryCellData> categoryProvider) {
		this.categoryProvider = categoryProvider;

		mainPanel = new VerticalPanel();

		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		Label categoryLabel = new Label("Kategorie");
		categoryLabel.addStyleName("filterLabel categoryFilterLabel");
		headerPanel.add(categoryLabel);
		toolsButton = Buttons.showTools();
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

		cell = new CategoryCell();
		categoryList = new CellList<CategoryCellData>(cell, GWT.<FilterListResources> create(FilterListResources.class));
		categoryList.setPageSize(30);
		categoryList.addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
			@Override
			public void onRowCountChange(RowCountChangeEvent event) {
				if (toolPanel != null) {
					refreshToolsPanel();
				}
			}
		});

		final SingleSelectionModel<CategoryCellData> selectionModel = new SingleSelectionModel<CategoryCellData>();
		categoryList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				if (selectedCategory != null) {
					selectedCategory.setSelected(false);
				}
				selectedCategory = selectionModel.getSelectedObject();
				if (selectionModel.getSelectedObject() != null) {
					selectedCategory.setSelected(true);
					panelMediator.setHeaderColor(selectedCategory.getCategoryDTO().getBorderColor());
					panelMediator.setSelectedCategory(selectedCategory.getCategoryDTO());
				} else {
					selectedCategory = null;
					panelMediator.resetHeaderColor();
					panelMediator.setSelectedCategory(null);
				}
			}
		});
		categoryProvider.addDataDisplay(categoryList);

		categoryList.addStyleName("categoryList");
		mainPanel.add(categoryList);

		createAnchor = new SimplePanel();
		mainPanel.add(createAnchor);

		return mainPanel;
	}

	private void toggleTools() {
		if (toolPanel != null) {
			hideTools();
		} else {
			showTools();
		}
	}

	private void showTools() {
		toolPanel = new CategoryToolPanel(this);
		createPanel = new CategoryCreatePanel(this, panelMediator);
		int right = categoryList.getAbsoluteLeft() + categoryList.getOffsetWidth() - 1;
		int top = categoryList.getAbsoluteTop() + 1;
		toolPanel.show(right, top);
		Widget createWidget = createPanel.show();
		createAnchor.add(createWidget);
	}
	
	private void hideTools() {
		toolPanel.hide();
		toolPanel = null;
		createPanel.hide();
		createAnchor.clear();
	}

	public void refreshToolsPanel() {
		hideTools();
		showTools();
	}

	public void delete(CategoryCellData category) {
		panelMediator.deleteCategory(category);
	}

	public void edit(CategoryCellData category) {
		if (category.getCategoryDTO().isInEditMode()) {
			category.getCategoryDTO().setInEditMode(false);
		} else {
			category.getCategoryDTO().setInEditMode(true);
		}
		categoryProvider.refresh();
	}
	
	public void moveUp(CategoryCellData category) {
		List<CategoryCellData> list = categoryProvider.getList();
		int idx = list.indexOf(category);
		if (idx < 1) {
			return;
		}
		CategoryCellData first = list.get(idx - 1);
		CategoryCellData second = list.get(idx);
		int tmp = first.getCategoryDTO().getSortIndex();
		first.getCategoryDTO().setSortIndex(second.getCategoryDTO().getSortIndex());
		second.getCategoryDTO().setSortIndex(tmp);
		list.remove(idx);
		list.add(idx - 1, second);
		update(first);
		update(second);
		refreshToolsPanel();
	}
	
	public void moveDown(CategoryCellData category) {
		List<CategoryCellData> list = categoryProvider.getList();
		int idx = list.indexOf(category);
		if (idx > list.size() - 2) {
			return;
		}
		moveUp(list.get(idx + 1));
	}
	
	public void update(CategoryCellData category) {
		panelMediator.updateCategory(category.getCategoryDTO());
	}

	public CategoryDTO getSelectedCategory() {
		return selectedCategory.getCategoryDTO();
	}

	public PanelMediator getPanelMediator() {
		return panelMediator;
	}

	class CategoryCell extends AbstractCell<CategoryCellData> {

		public CategoryCell() {
			super("change", "keydown");
		}

		@Override
		public void onBrowserEvent(Context context, Element parent, CategoryCellData value, NativeEvent event, ValueUpdater<CategoryCellData> valueUpdater) {
			if (value == null) {
				return;
			}
			super.onBrowserEvent(context, parent, value, event, valueUpdater);
			String evs = event.getType();

			if ("change".equals(event.getType())) {
				updateFromCell(parent, value);
			}
		}

		@Override
		public void onEnterKeyDown(Context context, Element parent, CategoryCellData value, NativeEvent event, ValueUpdater<CategoryCellData> valueUpdater) {
			if (value == null) {
				return;
			}
			updateFromCell(parent, value);
		}

		private void updateFromCell(Element parent, CategoryCellData value) {
			if (value.getCategoryDTO().isInEditMode()) {
				value.setName(readCategoryName(parent));
				value.getCategoryDTO().setInEditMode(false);
				update(value);
			}
		}

		protected String readCategoryName(Element parent) {
			InputElement e = parent.getFirstChild().getFirstChild().cast();
			return e.getValue();
		}

		@Override
		public void render(Context context, CategoryCellData category, SafeHtmlBuilder sb) {
			if (category == null) {
				return;
			}

			String css;
			String style;
			if (category.isSelected()) {
				css = "categorySelected";
				style = createSelectedStyle(category);
			} else {
				css = "categoryUnselected";
				style = "";
			}

			sb.appendHtmlConstant("<div class=\"" + css + "\" style=\"" + style + "\">");
			if (category.getCategoryDTO().isInEditMode()) {
				sb.appendHtmlConstant("<input type=\"text\" value=\"" + category.getName() + "\" style=\"height: 19px; border: 1px inset;\" />");
			} else {
				sb.appendHtmlConstant(category.getName());
			}
			sb.appendHtmlConstant("</div>");
		}

		private String createSelectedStyle(CategoryCellData cellData) {
			CategoryDTO dto = cellData.getCategoryDTO();
			String style = "background-color: " + dto.getBackgroundColor() + "; " + "border: 2px solid " + dto.getBorderColor() + ";";
			return style;
		}
	}

	public void showCreatePopup() {

	}

	public void clearSelection() {

	}

	public FilterDataProvider<CategoryCellData> getCategoryProvider() {
		return categoryProvider;
	}

	public void setCategoryProvider(FilterDataProvider<CategoryCellData> categoryProvider) {
		this.categoryProvider = categoryProvider;
	}

}
