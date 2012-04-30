package com.xfashion.client;

import com.google.gwt.core.client.EntryPoint;
import com.xfashion.client.at.ArticleTypeDatabase;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Xfashion implements EntryPoint {

	ArticleTypeDatabase articleTypeDatabase;

	public void onModuleLoad() {
		articleTypeDatabase = new ArticleTypeDatabase();
		PanelMediator panelMediator = new PanelMediator();
		panelMediator.setXfashion(this);
		panelMediator.setArticleTypeDatabase(articleTypeDatabase);
		articleTypeDatabase.setApplicationLoadListener(panelMediator);
	}		
	
}
