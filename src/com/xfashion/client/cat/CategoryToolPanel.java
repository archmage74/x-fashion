package com.xfashion.client.cat;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.tool.Buttons;
import com.xfashion.client.tool.DeleteClickHandler;
import com.xfashion.client.tool.DownClickHandler;
import com.xfashion.client.tool.EditClickHandler;
import com.xfashion.client.tool.UpClickHandler;

public class CategoryToolPanel {

	private CategoryPanel categoryPanel;
	
	private PopupPanel toolsPopup;
	
	public CategoryToolPanel(CategoryPanel categoryPanel) {
		this.categoryPanel = categoryPanel;
	}
	
	public PopupPanel show(int right, int top) {
		toolsPopup = new PopupPanel();
		VerticalPanel panel = new VerticalPanel();
		for (CategoryCellData cat : categoryPanel.getCategoryProvider().getList()) {
			HorizontalPanel toolPanel = new HorizontalPanel();

			Image editButton = Buttons.edit();
			editButton.addClickHandler(new EditClickHandler<CategoryCellData>(cat, categoryPanel));
			toolPanel.add(editButton);

			Image upButton = Buttons.up();
			upButton.addClickHandler(new UpClickHandler<CategoryCellData>(cat, categoryPanel));
			toolPanel.add(upButton);

			Image downButton = Buttons.down();
			downButton.addClickHandler(new DownClickHandler<CategoryCellData>(cat, categoryPanel));
			toolPanel.add(downButton);

			Image deleteButton = Buttons.delete();
			deleteButton.addClickHandler(new DeleteClickHandler<CategoryCellData>(cat, categoryPanel));
			toolPanel.add(deleteButton);

			toolPanel.setHeight("39px");
			toolPanel.setStyleName("toolGrid");
			panel.add(toolPanel);

		}
		toolsPopup.add(panel);

		int left = right - 56;
		toolsPopup.setStyleName("toolsPanel");
		toolsPopup.setPopupPosition(left, top);
		toolsPopup.show();
		
		return toolsPopup;
	}

	public void hide() {
		toolsPopup.hide();
		toolsPopup.clear();
	}
	
}
