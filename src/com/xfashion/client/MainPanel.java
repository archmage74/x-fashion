package com.xfashion.client;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.xfashion.client.at.ArticleTypeManagement;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.notepad.NotepadManagement;
import com.xfashion.client.user.UserManagement;
import com.xfashion.client.user.UserProfile;

public class MainPanel implements ErrorHandler {

	private boolean DEV_MODE = true;
	
	private Panel contentPanel;

	private PanelMediator panelMediator;
	
	private ArticleTypeDatabase articleTypeDatabase;

	private ArticleTypeManagement articleTypeManagement;

	private UserManagement userManagement;
	
	private NotepadManagement notepadManagement;
	
	private UserProfile userProfile;

	private ErrorPopup errorPopup;
	
	public MainPanel() {
		articleTypeDatabase = new ArticleTypeDatabase();
		articleTypeDatabase.init();
		panelMediator = new PanelMediator();
		panelMediator.setArticleTypeDatabase(articleTypeDatabase);

		contentPanel = new SimplePanel();
		RootPanel.get("mainPanelContainer").add(contentPanel);
		articleTypeManagement = new ArticleTypeManagement(); 
		userManagement = new UserManagement();
		notepadManagement = new NotepadManagement(articleTypeDatabase);
		userProfile = new UserProfile();

		Xfashion.eventBus.addHandler(ErrorEvent.TYPE, this);
	}

	public void showArticleTypePanel() {
		if (!DEV_MODE && UserManagement.loggedInUser == null) {
			showUserProfilePanel();
		} else {
			contentPanel.clear();
			Panel panel = articleTypeManagement.getPanel(articleTypeDatabase, panelMediator, notepadManagement.getArticleProvider());
			contentPanel.add(panel);
		}
	}

	public void showUserManagementPanel() {
		if (!DEV_MODE && UserManagement.loggedInUser == null) {
			showUserProfilePanel();
		} else {
			contentPanel.clear();
			Panel panel = userManagement.getPanel();
			contentPanel.add(panel);
		}
	}
	
	public void showNotepadManagementProfilePanel() {
		if (!DEV_MODE && UserManagement.loggedInUser == null) {
			showUserProfilePanel();
		} else {
			contentPanel.clear();
			Panel panel = notepadManagement.getPanel();
			contentPanel.add(panel);
		}
	}

	public void showUserProfilePanel() {
		contentPanel.clear();
		Panel panel = userProfile.getPanel();
		contentPanel.add(panel);
	}

	@Override
	public void onError(ErrorEvent event) {
		if (errorPopup == null) {
			errorPopup = new ErrorPopup();
		}
		errorPopup.showPopup(event.getErrorMessage());
	}

	public void test() {
//		ColorFlashAnimation cfa = new ColorFlashAnimation(sp);
//		cfa.run();
	}

}
