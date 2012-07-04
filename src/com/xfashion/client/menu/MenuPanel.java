package com.xfashion.client.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.MainPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.MenuMessages;
import com.xfashion.client.user.LoginEvent;
import com.xfashion.client.user.LoginHandler;
import com.xfashion.client.user.UserManagement;
import com.xfashion.shared.UserRole;

public class MenuPanel implements LoginHandler {

	private MenuMessages menuMessages;
	
	private MainPanel mainPanel;
	
	private VerticalPanel navPanel;
	
	private Label loggedInLabel;
	
	public MenuPanel(MainPanel mainPanel) {
		menuMessages = GWT.create(MenuMessages.class);
		this.mainPanel = mainPanel;
		Xfashion.eventBus.addHandler(LoginEvent.TYPE, this);
	}
	
	@Override
	public void onLogin(LoginEvent event) {
		redraw();
		if (loggedInLabel != null) {
			loggedInLabel.setText(event.getUser().getUsername());
		}
	}
	
	public void addMenuPanel() {
		navPanel = new VerticalPanel();
		navPanel.addStyleName("navigationPanel");
		RootPanel.get("navPanelContainer").add(navPanel);
		
		Panel loggedInPanel = new HorizontalPanel();
		Label loggedInHeader = new Label(menuMessages.loggedInAs() + ": ");
		loggedInPanel.add(loggedInHeader);
		loggedInLabel = new Label();
		loggedInPanel.add(loggedInLabel);
		navPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		navPanel.add(loggedInPanel);
		
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		menu.setWidth("1200px");
		menu.setAnimationEnabled(true);

		menu.addItem(createArticleTypeMenuItem());
		menu.addItem(createStockMenuItem());
		menu.addItem(createUserProfileMenuItem());
		menu.addItem(createSellStatisticMenuItem());

		if (UserManagement.hasRole(UserRole.DEVELOPER, UserRole.ADMIN)) {
			menu.addItem(createUserManagementMenuItem());
		}

		if (UserManagement.hasRole(UserRole.DEVELOPER)) {
			menu.addItem(createTestMenuItem());
		}

		navPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		navPanel.add(menu);
	}
	
	public void redraw() {
		navPanel.clear();
		RootPanel.get("navPanelContainer").remove(navPanel);
		navPanel = null;
		addMenuPanel();
	}

	private MenuItem createTestMenuItem() {
		Command test = new Command() {
			public void execute() {
				mainPanel.test();
			}
		};
		MenuItem testItem = new MenuItem(menuMessages.test(), test);
		return testItem;
	}

	private MenuItem createUserProfileMenuItem() {
		Command showUserProfile = new Command() {
			public void execute() {
				mainPanel.showUserProfilePanel();
			}
		};
		MenuItem userProfileItem = new MenuItem(menuMessages.userProfile(), showUserProfile);
		return userProfileItem;
	}

	private MenuItem createUserManagementMenuItem() {
		Command showUserManagement = new Command() {
			public void execute() {
				mainPanel.showUserManagementPanel();
			}
		};
		MenuItem userManagementItem = new MenuItem(menuMessages.userManagement(), showUserManagement);
		return userManagementItem;
	}

	private MenuItem createArticleTypeMenuItem() {
		Command showArticleType = new Command() {
			public void execute() {
				mainPanel.showArticleTypePanel();
			}
		};
		MenuItem articleTypeItem = new MenuItem(menuMessages.articleType(), showArticleType);
		return articleTypeItem;
	}

	private MenuItem createStockMenuItem() {
		Command showStock = new Command() {
			public void execute() {
				mainPanel.showStockPanel();
			}
		};
		MenuItem articleTypeItem = new MenuItem(menuMessages.stock(), showStock);
		return articleTypeItem;
	}

	private MenuItem createSellStatisticMenuItem() {
		Command showStock = new Command() {
			public void execute() {
				mainPanel.showSellStatsticPanel();
			}
		};
		MenuItem articleTypeItem = new MenuItem(menuMessages.sellStatistic(), showStock);
		return articleTypeItem;
	}

}
