package com.xfashion.client.at;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.Xfashion;
import com.xfashion.client.brand.BrandPanel;
import com.xfashion.client.cat.CategoryPanel;
import com.xfashion.client.color.ColorPanel;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.name.NamePanel;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.notepad.NotepadPanel;
import com.xfashion.client.notepad.NotepadStartMaximizeEvent;
import com.xfashion.client.notepad.NotepadStartMaximizeHandler;
import com.xfashion.client.notepad.NotepadStartMinimizeEvent;
import com.xfashion.client.notepad.NotepadStartMinimizeHandler;
import com.xfashion.client.size.SizePanel;
import com.xfashion.client.style.StylePanel;

public class ArticleTypeManagement implements NotepadStartMaximizeHandler, NotepadStartMinimizeHandler {

	HorizontalPanel panel;
	
	CategoryPanel categoryPanel;
	BrandPanel brandPanel;
	StylePanel stylePanel;
	SizePanel sizePanel;
	ColorPanel colorPanel;
	NamePanel namePanel;
	NotepadPanel notepadPanel;
	ArticleTypePanel articleTypePanel;

	public Panel getPanel(ArticleTypeDatabase articleTypeDatabase, PanelMediator panelMediator, ArticleAmountDataProvider notepadArticleProvider) {
		if (panel == null) {
			panel = new HorizontalPanel();
	
			categoryPanel = new CategoryPanel(panelMediator, articleTypeDatabase.getCategoryProvider());
			brandPanel = new BrandPanel(panelMediator, articleTypeDatabase.getBrandProvider());
			stylePanel = new StylePanel(panelMediator, articleTypeDatabase.getStyleProvider());
			sizePanel = new SizePanel(panelMediator, articleTypeDatabase.getSizeProvider());
			colorPanel = new ColorPanel(panelMediator, articleTypeDatabase.getColorProvider());
			namePanel = new NamePanel(articleTypeDatabase.getNameProvider());
			articleTypePanel = new ArticleTypePanel(panelMediator);
			notepadPanel = new NotepadPanel(panelMediator);

			panel.add(categoryPanel.createPanel());
			panel.add(brandPanel.createPanel());
			panel.add(stylePanel.createPanel());
			panel.add(namePanel.createPanel());
			panel.add(colorPanel.createPanel());
			panel.add(sizePanel.createPanel());
			panel.add(articleTypePanel.createPanel(articleTypeDatabase.getArticleTypeProvider(), articleTypeDatabase.getNameOracle()));
			panel.add(notepadPanel.createPanel(notepadArticleProvider));
		}
		
		Xfashion.eventBus.addHandler(NotepadStartMaximizeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(NotepadStartMinimizeEvent.TYPE, this);
		
		return panel;
	}

	@Override
	public void onNotepadStartMaximize(NotepadStartMaximizeEvent event) {
		brandPanel.minimize();
		stylePanel.minimize();
		sizePanel.minimize();
		colorPanel.minimize();
		namePanel.minimize();
	}

	@Override
	public void onNotepadStartMinimize(NotepadStartMinimizeEvent event) {
		brandPanel.maximize();
		stylePanel.maximize();
		sizePanel.maximize();
		colorPanel.maximize();
		namePanel.maximize();
	}


}