package com.xfashion.client.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.xfashion.client.MainPanel;
import com.xfashion.client.resources.MenuMessages;

public class MenuPanel {

	private MenuMessages menuMessages;
	
	private MainPanel mainPanel;
	
	public MenuPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	public void addNavPanel() {
		menuMessages = GWT.create(MenuMessages.class);

		SimplePanel navPanel = new SimplePanel();
		RootPanel.get("navPanelContainer").add(navPanel);

		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		menu.setWidth("1200px");
		menu.setAnimationEnabled(true);

		Command showArticleType = new Command() {
			public void execute() {
				mainPanel.showArticleTypePanel();
			}
		};
		Command showUserManagement = new Command() {
			public void execute() {
				mainPanel.showUserManagementPanel();
			}
		};

		MenuItem articleTypeItem = new MenuItem(menuMessages.articleType(), showArticleType);
		menu.addItem(articleTypeItem);
		MenuItem userManagementItem = new MenuItem(menuMessages.userManagement(), showUserManagement);
		menu.addItem(userManagementItem);

		navPanel.add(menu);
	}

}
