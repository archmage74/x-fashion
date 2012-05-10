package com.xfashion.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.tool.Buttons;
import com.xfashion.client.tool.DeleteClickHandler;
import com.xfashion.client.tool.DownClickHandler;
import com.xfashion.client.tool.EditClickHandler;
import com.xfashion.client.tool.UpClickHandler;
import com.xfashion.shared.FilterCellData;

public abstract class ToolPanel<T extends FilterCellData<?>> {

	protected PanelMediator panelMediator;

	protected FilterPanel<T> parentPanel;

	protected PopupPanel toolsPopup;

	protected Panel createAnchor;

	protected TextBox createTextBox;

	protected ErrorMessages errorMessages;

	public ToolPanel(FilterPanel<T> parentPanel, PanelMediator panelMediator) {
		errorMessages = GWT.create(ErrorMessages.class);
		this.parentPanel = parentPanel;
		this.panelMediator = panelMediator;
	}

	protected abstract void createDTOFromPanel();
	
	public void show(int right, int top, Panel createAnchor) {
		setCreateAnchor(createAnchor);
		showTools(right, top);
		showCreatePanel(createAnchor);
	}
	
	protected void showTools(int right, int top) {
		toolsPopup = new PopupPanel();
		VerticalPanel panel = new VerticalPanel();
		for (T cellData : parentPanel.getDataProvider().getList()) {
			HorizontalPanel toolPanel = new HorizontalPanel();

			Image editButton = Buttons.edit();
			editButton.addClickHandler(new EditClickHandler<T>(cellData, parentPanel));
			toolPanel.add(editButton);

			Image upButton = Buttons.up();
			upButton.addClickHandler(new UpClickHandler<T>(cellData, parentPanel));
			toolPanel.add(upButton);

			Image downButton = Buttons.down();
			downButton.addClickHandler(new DownClickHandler<T>(cellData, parentPanel));
			toolPanel.add(downButton);

			Image deleteButton = Buttons.delete();
			deleteButton.addClickHandler(new DeleteClickHandler<T>(cellData, parentPanel));
			toolPanel.add(deleteButton);

			toolPanel.setHeight(cellData.getHeight() + "px");
			toolPanel.setStyleName("toolGrid");
			panel.add(toolPanel);

		}
		toolsPopup.add(panel);

		int left = right - 58;
		toolsPopup.setStyleName("toolsPanel");
		toolsPopup.setPopupPosition(left, top);
		toolsPopup.show();
	}
	
	protected void showCreatePanel(Panel createAnchor) {
		Widget c = createCreatePanel();
		createAnchor.add(c);
	}

	public void hide() {
		for (T cellData : parentPanel.getDataProvider().getList()) {
			if (cellData.isInEditMode()) {
				cellData.setInEditMode(false);
			}
		}
		toolsPopup.hide();
		toolsPopup.clear();
		toolsPopup = null;
		createAnchor.clear();
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
		int numCells = parentPanel.getDataProvider().getList().size();
		int idx = 0;
		if (numCells != 0) {
			idx = parentPanel.getDataProvider().getList().get(numCells - 1).getSortIndex() + 1;
		}
		dto.setSortIndex(idx);
	}

	public Panel getCreateAnchor() {
		return createAnchor;
	}

	public void setCreateAnchor(Panel createAnchor) {
		this.createAnchor = createAnchor;
	}

}
