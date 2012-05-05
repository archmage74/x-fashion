package com.xfashion.client.at;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.Xfashion;
import com.xfashion.client.brand.BrandPanel;
import com.xfashion.client.cat.CategoryPanel;
import com.xfashion.client.color.ColorPanel;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.notepad.NotepadMaximizedEvent;
import com.xfashion.client.notepad.NotepadMaximizedHandler;
import com.xfashion.client.notepad.NotepadPanel;
import com.xfashion.client.size.SizePanel;
import com.xfashion.client.style.StylePanel;

public class ArticleTypeManagement implements NotepadMaximizedHandler {

	HorizontalPanel panel;
	
	BrandPanel brandPanel;
	StylePanel stylePanel;
	SizePanel sizePanel;
	ColorPanel colorPanel;
	NotepadPanel notepadPanel;

	public Panel getPanel(ArticleTypeDatabase articleTypeDatabase, PanelMediator panelMediator, ArticleTypeDataProvider notepadArticleProvider) {
		if (panel == null) {
			panel = new HorizontalPanel();
	
			CategoryPanel categoryPanel = new CategoryPanel(panelMediator, articleTypeDatabase.getCategoryProvider());
			panel.add(categoryPanel.createPanel());
	
			brandPanel = new BrandPanel(panelMediator, articleTypeDatabase.getBrandProvider());
			panel.add(brandPanel.createPanel());
	
			stylePanel = new StylePanel(panelMediator, articleTypeDatabase.getStyleProvider());
			panel.add(stylePanel.createPanel());
	
			sizePanel = new SizePanel(panelMediator, articleTypeDatabase.getSizeProvider());
			panel.add(sizePanel.createPanel());
	
			colorPanel = new ColorPanel(panelMediator, articleTypeDatabase.getColorProvider());
			panel.add(colorPanel.createPanel());
	
			ArticleTypePanel articleTypePanel = new ArticleTypePanel(panelMediator);
			panel.add(articleTypePanel.createPanel(articleTypeDatabase.getArticleTypeProvider(), articleTypeDatabase.getNameOracle()));
	
			notepadPanel = new NotepadPanel(panelMediator);
			panel.add(notepadPanel.createPanel(notepadArticleProvider));
		}
		
		Xfashion.eventBus.addHandler(NotepadMaximizedEvent.TYPE, this);
		
		return panel;
	}

	@Override
	public void onNotepadMaximized(NotepadMaximizedEvent event) {
		brandPanel.minimize();
		stylePanel.minimize();
		sizePanel.minimize();
		colorPanel.minimize();
	}


}
