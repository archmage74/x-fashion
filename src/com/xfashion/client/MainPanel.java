package com.xfashion.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.at.ArticleTypeDatabase;
import com.xfashion.client.at.ArticleTypePanel;
import com.xfashion.client.brand.BrandPanel;
import com.xfashion.client.cat.CategoryPanel;
import com.xfashion.client.color.ColorPanel;
import com.xfashion.client.size.SizePanel;
import com.xfashion.client.style.StylePanel;

public class MainPanel {
	
	public void addMainPanel(PanelMediator panelMediator) {
		VerticalPanel panel = new VerticalPanel();
		RootPanel.get("mainPanelContainer").add(panel);
		
		Panel mainPanel = new HorizontalPanel();
		ArticleTypeDatabase articleTypeDatabase = panelMediator.getArticleTypeDatabase();
		
		articleTypeDatabase.setApplicationLoadListener(panelMediator);
		articleTypeDatabase.setApplicationErrorListener(panelMediator);

		CategoryPanel categoryPanel = new CategoryPanel(panelMediator, articleTypeDatabase.getCategoryProvider());
		mainPanel.add(categoryPanel.createPanel());
		
		BrandPanel brandPanel = new BrandPanel(panelMediator, articleTypeDatabase.getBrandProvider());
		mainPanel.add(brandPanel.createPanel());
		
		StylePanel stylePanel = new StylePanel(panelMediator, articleTypeDatabase.getStyleProvider());
		mainPanel.add(stylePanel.createPanel());
		
		SizePanel sizePanel = new SizePanel(panelMediator, articleTypeDatabase.getSizeProvider());
		mainPanel.add(sizePanel.createPanel());
		
		ColorPanel colorPanel = new ColorPanel(panelMediator, articleTypeDatabase.getColorProvider());
		mainPanel.add(colorPanel.createPanel());
		
		ArticleTypePanel articleTypePanel = new ArticleTypePanel(panelMediator);
		mainPanel.add(articleTypePanel.createPanel(articleTypeDatabase.getArticleTypeProvider(), articleTypeDatabase.getNameOracle()));
		
		panel.add(mainPanel);
	}
	
	public void addNavPanel(final PanelMediator panelMediator) {
		HorizontalPanel navPanel = new HorizontalPanel();
		RootPanel.get("navPanelContainer").add(navPanel);
		
		Button createCategoriesButton = new Button("create categories");
		createCategoriesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				panelMediator.getArticleTypeDatabase().createCategories();
			}
		});
		
		navPanel.add(createCategoriesButton);
	}


}
