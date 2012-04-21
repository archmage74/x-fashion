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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.xfashion.client.DeleteClickHandler;
import com.xfashion.client.DownClickHandler;
import com.xfashion.client.EditClickHandler;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.ICrud;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.ToolDeleteButton;
import com.xfashion.client.ShowToolsButton;
import com.xfashion.client.ToolDownButton;
import com.xfashion.client.ToolEditButton;
import com.xfashion.client.ToolUpButton;
import com.xfashion.client.UpClickHandler;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.FilterListResources;
import com.xfashion.shared.CategoryDTO;

public class CategoryPanel extends FilterPanel implements ICrud<CategoryDTO> {

	private CategoryDTO selectedCategory;

	private CellList<CategoryDTO> categoryList;

	private VerticalPanel mainPanel;

	private SimplePanel addCategoryPanel;

	private CategoryCell cell;

	private Button toolsButton;

	private PopupPanel toolsPopup;

	private ListDataProvider<CategoryDTO> categoryProvider;

	private ErrorMessages errorMessages;

	private String[][] colorSchemas = { { "#76A573", "#466944" }, { "#6F77AA", "#2B3781" }, { "#8AADB8", "#578C9B" }, { "#9F7F79", "#71433A" },
			{ "#9EC1AA", "#74A886" }, { "#A26E7B", "#7C3044" }, { "#AD89B8", "#89559A" }, { "#B2B7D9", "#919BCA" }, { "#C1AEA5", "#A78C7E" },
			{ "#CEAFD0", "#B98DBC" }, { "#D7A899", "#C5846E" } };

	public CategoryPanel(PanelMediator panelMediator) {
		super(panelMediator);
		errorMessages = GWT.create(ErrorMessages.class);
		panelMediator.setCategoryPanel(this);
	}

	public Panel createPanel(ListDataProvider<CategoryDTO> categoryProvider) {
		this.categoryProvider = categoryProvider;

		mainPanel = new VerticalPanel();

		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		Label categoryLabel = new Label("Kategorie");
		categoryLabel.addStyleName("filterLabel categoryFilterLabel");
		headerPanel.add(categoryLabel);
		toolsButton = new ShowToolsButton();
		toolsButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				toggleTools();
			}
		});
		headerPanel.add(toolsButton);

		mainPanel.add(headerPanel);
		setHeaderColor(null);

		cell = new CategoryCell();
		categoryList = new CellList<CategoryDTO>(cell, GWT.<FilterListResources> create(FilterListResources.class));
		categoryList.setPageSize(30);
		categoryList.addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
			@Override
			public void onRowCountChange(RowCountChangeEvent event) {
				if (toolsPopup != null) {
					refreshToolsPanel();
				}
			}
		});

		final SingleSelectionModel<CategoryDTO> selectionModel = new SingleSelectionModel<CategoryDTO>();
		categoryList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedCategory = selectionModel.getSelectedObject();
				if (selectionModel.getSelectedObject() != null) {
					panelMediator.setHeaderColor(selectedCategory.getBorderColor());
				} else {
					panelMediator.resetHeaderColor();
				}
				panelMediator.setSelectedCategory(selectedCategory);
			}
		});
		categoryProvider.addDataDisplay(categoryList);

		categoryList.addStyleName("categoryList");
		mainPanel.add(categoryList);

		addCategoryPanel = new SimplePanel();
		mainPanel.add(addCategoryPanel);

		return mainPanel;
	}

	private void toggleTools() {
		if (toolsPopup != null) {
			hideTools();
		} else {
			showTools();
		}
	}

	private void showTools() {
		toolsPopup = new PopupPanel();
		VerticalPanel panel = new VerticalPanel();
		for (CategoryDTO cat : categoryProvider.getList()) {
			HorizontalPanel toolPanel = new HorizontalPanel();

			ToolEditButton editButton = new ToolEditButton();
			editButton.addClickHandler(new EditClickHandler<CategoryDTO>(cat, this));
			toolPanel.add(editButton);

			ToolUpButton upButton = new ToolUpButton();
			upButton.addClickHandler(new UpClickHandler<CategoryDTO>(cat, this));
			toolPanel.add(upButton);

			ToolDownButton downButton = new ToolDownButton();
			downButton.addClickHandler(new DownClickHandler<CategoryDTO>(cat, this));
			toolPanel.add(downButton);

			ToolDeleteButton deleteButton = new ToolDeleteButton();
			deleteButton.addClickHandler(new DeleteClickHandler<CategoryDTO>(cat, this));
			toolPanel.add(deleteButton);

			toolPanel.setHeight("39px");
			toolPanel.setStyleName("toolGrid");
			panel.add(toolPanel);

		}
		toolsPopup.add(panel);

		int left = categoryList.getAbsoluteLeft() + categoryList.getOffsetWidth() - 68;
		int top = categoryList.getAbsoluteTop() + 1;
		toolsPopup.setStyleName("toolsPanel");
		toolsPopup.setPopupPosition(left, top);
		toolsPopup.show();

		Grid addCategoryGrid = new Grid(3, 1);

		final TextBox addCategoryTextBox = new TextBox();
		addCategoryGrid.setWidget(0, 0, addCategoryTextBox);

		final ListBox addCategoryColorListBox = new ListBox();
		for (String[] color : colorSchemas) {
			addCategoryColorListBox.addItem(color[0] + " / " + color[1]);
		}
		addCategoryGrid.setWidget(1, 0, addCategoryColorListBox);

		Button addCategoryButton = new Button("Anlegen");
		addCategoryButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				CategoryDTO category = new CategoryDTO();
				String name = addCategoryTextBox.getText();
				if (name == null || name.length() == 0) {
					panelMediator.showError(errorMessages.categoryCreateNoName());
					return;
				}
				category.setName(name);
				String[] color = colorSchemas[addCategoryColorListBox.getSelectedIndex()];
				category.setBackgroundColor(color[0]);
				category.setBorderColor(color[1]);
				int numCats = categoryProvider.getList().size();
				int idx = 0;
				if (numCats != 0) {
					idx = categoryProvider.getList().get(numCats - 1).getSortIndex() + 1;
				}
				category.setSortIndex(idx);
				panelMediator.createCategory(category);
			}
		});
		addCategoryGrid.setWidget(2, 0, addCategoryButton);

		addCategoryPanel.add(addCategoryGrid);
	}

	private void hideTools() {
		toolsPopup.hide();
		addCategoryPanel.clear();
		toolsPopup = null;
	}

	public void refreshToolsPanel() {
		hideTools();
		showTools();
	}

	public void delete(CategoryDTO category) {
		panelMediator.deleteCategory(category);
	}

	public void edit(CategoryDTO category) {
		if (category.isInEditMode()) {
			category.setInEditMode(false);
		} else {
			category.setInEditMode(true);
		}
		categoryProvider.refresh();
	}
	
	public void moveUp(CategoryDTO category) {
		List<CategoryDTO> list = categoryProvider.getList();
		int idx = list.indexOf(category);
		if (idx < 1) {
			return;
		}
		CategoryDTO first = list.get(idx - 1);
		CategoryDTO second = list.get(idx);
		int tmp = first.getSortIndex();
		first.setSortIndex(second.getSortIndex());
		second.setSortIndex(tmp);
		list.remove(idx);
		list.add(idx - 1, second);
		update(first);
		update(second);
		refreshToolsPanel();
	}
	
	public void moveDown(CategoryDTO category) {
		List<CategoryDTO> list = categoryProvider.getList();
		int idx = list.indexOf(category);
		if (idx > list.size() - 2) {
			return;
		}
		moveUp(list.get(idx + 1));
	}
	
	public void update(CategoryDTO category) {
		panelMediator.updateCategory(category);
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

		public CategoryCell() {
			super("change", "keydown");
		}

		@Override
		public void onBrowserEvent(Context context, Element parent, CategoryDTO value, NativeEvent event, ValueUpdater<CategoryDTO> valueUpdater) {
			if (value == null) {
				return;
			}
			super.onBrowserEvent(context, parent, value, event, valueUpdater);

			if ("change".equals(event.getType())) {
				updateFromCell(parent, value);
			}
		}

		@Override
		public void onEnterKeyDown(Context context, Element parent, CategoryDTO value, NativeEvent event, ValueUpdater<CategoryDTO> valueUpdater) {
			if (value == null) {
				return;
			}
			updateFromCell(parent, value);
		}

		private void updateFromCell(Element parent, CategoryDTO value) {
			if (value.isInEditMode()) {
				value.setName(readCategoryName(parent));
				value.setInEditMode(false);
				update(value);
			}
		}

		protected String readCategoryName(Element parent) {
			InputElement e = parent.getFirstChild().getFirstChild().cast();
			return e.getValue();
		}

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
			if (category.isInEditMode()) {
				sb.appendHtmlConstant("<input type=\"text\" value=\"" + category.getName() + "\" style=\"height: 19px; border: 1px inset;\" />");
			} else {
				sb.appendHtmlConstant(category.getName());
			}
			sb.appendHtmlConstant("</div>");
		}

		private String createSelectedStyle(CategoryDTO dto) {
			String style = "background-color: " + dto.getBackgroundColor() + "; " + "border: 2px solid " + dto.getBorderColor() + ";";
			return style;
		}
	}

	public void showCreatePopup() {

	}

	public void clearSelection() {

	}

}
