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
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.tool.Buttons;
import com.xfashion.shared.FilterCellData;

public abstract class FilterPanel<T extends FilterCellData> implements ICrud<T> {

	protected Panel headerPanel; 
	
	protected PanelMediator panelMediator;
	
	protected FilterDataProvider<T> dataProvider;
	
	protected ToolPanel<T> toolPanel;
	
	protected Panel createAnchor;
	
	protected CellList<T> cellList;
	
	protected ErrorMessages errorMessages;
	
	public FilterPanel(PanelMediator panelMediator, FilterDataProvider<T> dataProvider) {
		errorMessages = GWT.create(ErrorMessages.class);
		this.panelMediator = panelMediator;
		this.dataProvider = dataProvider;
	}
	
	public abstract Panel createPanel();
	
	public abstract void clearSelection();
	
	public abstract void showCreatePopup();
	
	protected abstract ToolPanel<T> createToolPanel();
	
	public void setHeaderColor(String color) {
		if (color != null) {
			headerPanel.getElement().getStyle().setBackgroundColor(color);
		} else {
			headerPanel.getElement().getStyle().setBackgroundColor("#777");
		}
	}
	
	protected Panel createHeaderPanel(String title) {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");

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

		setHeaderColor(null);
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
		
	}
	
	public PanelMediator getPanelMediator() {
		return panelMediator;
	}

	public FilterDataProvider<T> getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(FilterDataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
	}

	public Panel getCreateAnchor() {
		return createAnchor;
	}

	public void setCreateAnchor(Panel createAnchor) {
		this.createAnchor = createAnchor;
	}

}
