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

public class MenuPanel implements LoginHandler {

	private MenuMessages menuMessages;
	
	private MainPanel mainPanel;
	
	private Label loggedInLabel;
	
	public MenuPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		Xfashion.eventBus.addHandler(LoginEvent.TYPE, this);
	}
	
	@Override
	public void onLogin(LoginEvent event) {
		if (loggedInLabel != null) {
			loggedInLabel.setText(event.getUser().getUsername());
		}
	}
	
	public void addNavPanel() {
		menuMessages = GWT.create(MenuMessages.class);
		
		VerticalPanel navPanel = new VerticalPanel();
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
		Command showNotepadManagementProfile = new Command() {
			public void execute() {
				mainPanel.showNotepadManagementProfilePanel();
			}
		};
		Command showUserProfile = new Command() {
			public void execute() {
				mainPanel.showUserProfilePanel();
			}
		};

		MenuItem articleTypeItem = new MenuItem(menuMessages.articleType(), showArticleType);
		menu.addItem(articleTypeItem);
		MenuItem userManagementItem = new MenuItem(menuMessages.userManagement(), showUserManagement);
		menu.addItem(userManagementItem);
		MenuItem notepadManagementItem = new MenuItem(menuMessages.notepadManagement(), showNotepadManagementProfile);
		menu.addItem(notepadManagementItem);
		MenuItem userProfileItem = new MenuItem(menuMessages.userProfile(), showUserProfile);
		menu.addItem(userProfileItem);

		navPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		navPanel.add(menu);
	}

}
