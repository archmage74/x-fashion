package com.xfashion.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.xfashion.client.at.ArticleTypeDatabase;
import com.xfashion.client.at.ArticleTypePanel;
import com.xfashion.client.brand.BrandPanel;
import com.xfashion.client.cat.CategoryPanel;
import com.xfashion.client.color.ColorPanel;
import com.xfashion.client.notepad.NotepadManagement;
import com.xfashion.client.size.SizePanel;
import com.xfashion.client.style.StylePanel;
import com.xfashion.client.user.UserManagement;
import com.xfashion.client.user.UserProfile;

public class MainPanel implements ErrorHandler {

	private boolean DEV_MODE = true;
	
	private Panel contentPanel;

	private Panel articleTypeManagement;

	private PanelMediator panelMediator;
	
	private ArticleTypeDatabase articleTypeDatabase;

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
			if (articleTypeManagement == null) {
				createArticleTypeManagement(articleTypeDatabase, panelMediator);
			}
			contentPanel.add(articleTypeManagement);
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

	private void createArticleTypeManagement(ArticleTypeDatabase articleTypeDatabase, PanelMediator panelMediator) {
		articleTypeManagement = new HorizontalPanel();

		CategoryPanel categoryPanel = new CategoryPanel(panelMediator, articleTypeDatabase.getCategoryProvider());
		articleTypeManagement.add(categoryPanel.createPanel());

		BrandPanel brandPanel = new BrandPanel(panelMediator, articleTypeDatabase.getBrandProvider());
		articleTypeManagement.add(brandPanel.createPanel());

		StylePanel stylePanel = new StylePanel(panelMediator, articleTypeDatabase.getStyleProvider());
		articleTypeManagement.add(stylePanel.createPanel());

		SizePanel sizePanel = new SizePanel(panelMediator, articleTypeDatabase.getSizeProvider());
		articleTypeManagement.add(sizePanel.createPanel());

		ColorPanel colorPanel = new ColorPanel(panelMediator, articleTypeDatabase.getColorProvider());
		articleTypeManagement.add(colorPanel.createPanel());

		ArticleTypePanel articleTypePanel = new ArticleTypePanel(panelMediator);
		articleTypeManagement.add(articleTypePanel.createPanel(articleTypeDatabase.getArticleTypeProvider(), articleTypeDatabase.getNameOracle()));
	}

	@Override
	public void onError(ErrorEvent event) {
		if (errorPopup == null) {
			errorPopup = new ErrorPopup();
		}
		errorPopup.showPopup(event.getErrorMessage());
	}

}
