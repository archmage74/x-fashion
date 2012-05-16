package com.xfashion.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.tool.Buttons;
import com.xfashion.shared.FilterCellData;

public abstract class FilterPanel2<T extends FilterCellData<?>> implements ICrud<T>, IsMinimizable {

	public static final int PANEL_MAX_WIDTH = 155;
	public static final int PANEL_MIN_WIDTH = 22;
	
	protected Panel headerPanel;
	protected Panel scrollPanel;
	protected Panel tablePanel;
	
	protected PanelMediator panelMediator;
	protected ListDataProvider<T> dataProvider;
	
	protected Panel createAnchor;
	protected TextBox createTextBox;
	
	protected boolean minimized = false;
	protected Image minmaxButton;
	
	protected boolean editMode = false;
	
	protected TextMessages textMessages;
	protected ErrorMessages errorMessages;
	protected ImageResources images;
	
	public FilterPanel2(PanelMediator panelMediator, ListDataProvider<T> dataProvider) {
		errorMessages = GWT.create(ErrorMessages.class);
		textMessages = GWT.create(TextMessages.class);
		images = GWT.<ImageResources>create(ImageResources.class);
		this.panelMediator = panelMediator;
		this.dataProvider = dataProvider;
	}
	
	public abstract Panel createTablePanel();
	
	public abstract void clearSelection();
	
	public abstract void hideTools();
	
	public abstract void showTools();
	
	protected abstract void createDTOFromPanel();
	
	public Panel createPanel() {
		tablePanel = createTablePanel();
		scrollPanel = new SimplePanel();
		scrollPanel.setStyleName("filterPanel");
		scrollPanel.add(tablePanel);
		return scrollPanel;
	}
	
	protected Panel createHeaderPanel(String title) {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");

		minmaxButton = new Image();
		if (isMinimized()) {
			minmaxButton.setResource(images.iconMaximize());
		} else {
			minmaxButton.setResource(images.iconMinimize());
		}
		minmaxButton.setStyleName("buttonMinMax");
		ClickHandler minmaxClickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				minmax();
			}
		};
		minmaxButton.addClickHandler(minmaxClickHandler);
		headerPanel.add(minmaxButton);
		
		Label label = new Label(title);
		label.addStyleName("filterLabel attributeFilterLabel");
		headerPanel.add(label);

		Image toolsButton = Buttons.showTools();
		ClickHandler toolsButtonClickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				toggleTools();
			}
		};
		toolsButton.addClickHandler(toolsButtonClickHandler);
		headerPanel.add(toolsButton);

		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clearSelection();
			}
		});

		return headerPanel;
	}
	
	public void toggleTools() {
		if (editMode) {
			editMode = false;
			hideTools();
		} else {
			showTools();
			editMode = true;
		}
	}
	
	protected List<Column<T, ?>> createToolsColumns() {
		List<Column<T, ?>> columns = new ArrayList<Column<T,?>>();
		columns.add(createEditColumn()); 
		columns.add(createUpColumn());
		columns.add(createDownColumn());
		columns.add(createDeleteColumn());
		return columns;
	}

	private Column<T, ImageResource> createEditColumn() {
		Column<T, ImageResource> tool = new Column<T, ImageResource>(new ImageResourceCell()) {
			@Override
			public ImageResource getValue(T object) {
				return images.iconEdit();
			}
		};
		tool.setCellStyleNames("buttonTool");
		return tool;
	}

	private Column<T, ImageResource> createUpColumn() {
		Column<T, ImageResource> tool = new Column<T, ImageResource>(new ImageResourceCell()) {
			@Override
			public ImageResource getValue(T object) {
				return images.iconUp();
			}
		};
		tool.setCellStyleNames("buttonTool");
		return tool;
	}

	private Column<T, ImageResource> createDownColumn() {
		Column<T, ImageResource> tool = new Column<T, ImageResource>(new ImageResourceCell()) {
			@Override
			public ImageResource getValue(T object) {
				return images.iconDown();
			}
		};
		tool.setCellStyleNames("buttonTool");
		return tool;
	}

	private Column<T, ImageResource> createDeleteColumn() {
		Column<T, ImageResource> tool = new Column<T, ImageResource>(new ImageResourceCell()) {
			@Override
			public ImageResource getValue(T object) {
				return images.iconDelete();
			}
		};
		tool.setCellStyleNames("buttonTool");
		return tool;
	}

	protected Widget createCreatePanel() {
		Grid createGrid = new Grid(2, 1);

		createTextBox = new TextBox();
		createTextBox.setMaxLength(12);
		createTextBox.setWidth("140px");
		createGrid.setWidget(0, 0, createTextBox);

		Button createButton = new Button("Anlegen");
		createButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				createDTOFromPanel();
			}
		});
		createGrid.setWidget(1, 0, createButton);

		return createGrid;
	}
	
	protected void fillDTOFromPanel(T dto) {
		String name = createTextBox.getText();
		if (name == null || name.length() == 0) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.createAttributeNoName()));
			return;
		}
		dto.setName(name);
		int numCells = this.getDataProvider().getList().size();
		int idx = 0;
		if (numCells != 0) {
			idx = this.getDataProvider().getList().get(numCells - 1).getSortIndex() + 1;
		}
		dto.setSortIndex(idx);
	}

	@Override
	public void moveUp(T item) {
		List<T> list = dataProvider.getList();
		int idx = list.indexOf(item);
		if (idx < 1) {
			return;
		}
		T first = list.get(idx - 1);
		T second = list.get(idx);
		Integer tmp = first.getSortIndex();
		first.setSortIndex(second.getSortIndex());
		second.setSortIndex(tmp);
		list.remove(idx);
		list.add(idx - 1, second);
		update(first);
		update(second);
	}
	
	@Override
	public void moveDown(T item) {
		List<T> list = getDataProvider().getList();
		int idx = list.indexOf(item);
		if (idx > list.size() - 2) {
			return;
		}
		moveUp(list.get(idx + 1));
	}
	
	@Override
	public void edit(T item) {
		if (item.isInEditMode()) {
			item.setInEditMode(false);
		} else {
			item.setInEditMode(true);
		}
		getDataProvider().refresh();
	}
	
	public void minmax() {
		if (isMinimized()) {
			maximize();
		} else {
			minimize();
		}
	}
	
	public void minimize() {
		if (!isMinimized()) {
			PanelWidthAnimation pwa = new PanelWidthAnimation(this, PANEL_MAX_WIDTH, PANEL_MIN_WIDTH);
			pwa.run(300);
		}
	}
	
	public void maximize() {
		if (isMinimized()) {
			PanelWidthAnimation pwa = new PanelWidthAnimation(this, PANEL_MIN_WIDTH, PANEL_MAX_WIDTH);
			pwa.run(300);
		}
	}
	
	@Override
	public void setWidth(int width) {
		scrollPanel.setWidth(width + "px");
	}
	
	public PanelMediator getPanelMediator() {
		return panelMediator;
	}

	public Panel getCreateAnchor() {
		return createAnchor;
	}

	public void setCreateAnchor(Panel createAnchor) {
		this.createAnchor = createAnchor;
	}

	public Panel getTablePanel() {
		return tablePanel;
	}

	public boolean isMinimized() {
		return minimized;
	}

	public void setMinimized(boolean minimized) {
		if (minimized) {
			if (!this.minimized) {
				minmaxButton.setResource(images.iconMaximize());
			}
		} else {
			if (this.minimized) {
				minmaxButton.setResource(images.iconMinimize());
			}
		}
		this.minimized = minimized;
	}

	public ListDataProvider<T> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(ListDataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
	}

}
