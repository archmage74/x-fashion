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
import com.xfashion.client.size.SizePanel;
import com.xfashion.client.style.StylePanel;
import com.xfashion.client.user.UserManagement;

public class MainPanel {
	
	private Panel contentPanel;
	
	private PanelMediator panelMediator;
	
	private Panel articleTypeManagement;
	
	private UserManagement userManagement;
	
	public MainPanel(PanelMediator panelMediator) {
		this.panelMediator = panelMediator;
		contentPanel = new SimplePanel();
		RootPanel.get("mainPanelContainer").add(contentPanel);
		userManagement = new UserManagement();
	}
	
	public void showArticleTypePanel() {
		contentPanel.clear();
		
		if (articleTypeManagement == null) {
			createArticleTypeManagement();
		}

		contentPanel.add(articleTypeManagement);
	}

	private void createArticleTypeManagement() {
		articleTypeManagement = new HorizontalPanel();
		ArticleTypeDatabase articleTypeDatabase = panelMediator.getArticleTypeDatabase();

		articleTypeDatabase.setApplicationLoadListener(panelMediator);
		articleTypeDatabase.setApplicationErrorListener(panelMediator);

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
	
	public void showUserManagementPanel() {
		contentPanel.clear();
		
		Panel panel = userManagement.getPanel();
		
		contentPanel.add(panel);
	}
	
}
