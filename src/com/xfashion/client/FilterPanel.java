package com.xfashion.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.tool.Buttons;
import com.xfashion.shared.FilterCellData;

public abstract class FilterPanel<T extends FilterCellData<?>> implements ICrud<T>, IsMinimizable {

	public static final int PANEL_MAX_WIDTH = 155;
	public static final int PANEL_MIN_WIDTH = 22;
	
	protected Panel headerPanel;
	protected Panel scrollPanel;
	protected Panel listPanel;
	
	protected PanelMediator panelMediator;
	
	protected ListDataProvider<T> dataProvider;
	
	protected ToolPanel<T> toolPanel;
	
	protected Panel createAnchor;
	
	protected CellList<T> cellList;
	
	protected boolean minimized = false;
	protected Image minmaxButton;
	
	protected TextMessages textMessages;
	protected ErrorMessages errorMessages;
	protected ImageResources images;
	
	public FilterPanel(PanelMediator panelMediator, ListDataProvider<T> dataProvider) {
		errorMessages = GWT.create(ErrorMessages.class);
		textMessages = GWT.create(TextMessages.class);
		images = GWT.<ImageResources>create(ImageResources.class);
		this.panelMediator = panelMediator;
		this.dataProvider = dataProvider;
	}
	
	public abstract Panel createListPanel();
	
	public abstract void clearSelection();
	
	protected abstract ToolPanel<T> createToolPanel();
	
	public Panel createPanel() {
		createListPanel();
		scrollPanel = new SimplePanel();
		scrollPanel.setStyleName("filterPanel");
		scrollPanel.add(listPanel);
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
		if (toolPanel != null) {
			hideTools();
		} else {
			showTools();
		}
	}

	public void refreshToolsPanel() {
		hideTools();
		showTools();
	}

	public void hideTools() {
		toolPanel.hide();
		toolPanel = null;
	}

	public void showTools() {
		toolPanel = createToolPanel();
		int right = cellList.getAbsoluteLeft() + cellList.getOffsetWidth() - 1;
		int top = cellList.getAbsoluteTop() + 1;
		toolPanel.show(right, top, getCreateAnchor());
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
		refreshToolsPanel();
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

	public ListDataProvider<T> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(ListDataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
	}

	public Panel getCreateAnchor() {
		return createAnchor;
	}

	public void setCreateAnchor(Panel createAnchor) {
		this.createAnchor = createAnchor;
	}

	public Panel getListPanel() {
		return listPanel;
	}

	public void setListPanel(Panel listPanel) {
		this.listPanel = listPanel;
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

}
