package com.xfashion.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.xfashion.client.at.ArticleTypeManagement;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.db.ArticleTypeService;
import com.xfashion.client.db.ArticleTypeServiceAsync;
import com.xfashion.client.notepad.NotepadManagement;
import com.xfashion.client.user.UserManagement;
import com.xfashion.client.user.UserProfile;
import com.xfashion.shared.SizeDTO;

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
		ArticleTypeServiceAsync articleTypeService = (ArticleTypeServiceAsync) GWT.create(ArticleTypeService.class);
		AsyncCallback<List<SizeDTO>> callback = new AsyncCallback<List<SizeDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(List<SizeDTO> result) {
				String str = "";
				for (SizeDTO s : result) {
					str = str + s.getName() + ", ";
				}
			}
		};
		articleTypeService.readSizes(callback);
	}

}
