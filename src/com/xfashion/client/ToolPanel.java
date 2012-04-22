package com.xfashion.client;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class ToolPanel<T extends FilterCellData> {

	private FilterPanel<T> parentPanel;
	
	private PopupPanel toolsPopup;
	
	public ToolPanel(FilterPanel<T> parentPanel) {
		this.parentPanel = parentPanel;
	}
	
	public PopupPanel show(int right, int top) {
		toolsPopup = new PopupPanel();
		VerticalPanel panel = new VerticalPanel();
		// for (CategoryDTO cat : parentPanel.get.getList()) {
		return null;
	}
}
