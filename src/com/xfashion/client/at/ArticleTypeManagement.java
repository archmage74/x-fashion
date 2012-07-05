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
import com.xfashion.client.notepad.event.NotepadStartMaximizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMaximizeHandler;
import com.xfashion.client.notepad.event.NotepadStartMinimizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMinimizeHandler;
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
			
			categoryPanel = new CategoryPanel(articleTypeDatabase.getCategoryProvider());
			panelMediator.setCategoryPanel(categoryPanel);
			brandPanel = new BrandPanel(articleTypeDatabase.getBrandProvider());
			panelMediator.setBrandPanel(brandPanel);
			stylePanel = new StylePanel(articleTypeDatabase.getCategoryProvider().getStyleProvider());
			panelMediator.setStylePanel(stylePanel);
			sizePanel = new SizePanel(articleTypeDatabase.getSizeProvider());
			panelMediator.setSizePanel(sizePanel);
			colorPanel = new ColorPanel(articleTypeDatabase.getColorProvider());
			panelMediator.setColorPanel(colorPanel);
			namePanel = new NamePanel(articleTypeDatabase.getNameProvider());
			articleTypePanel = new ArticleTypePanel(panelMediator);
			notepadPanel = new NotepadPanel(articleTypeDatabase);
			
			panel.add(categoryPanel.createPanel());
			panel.add(brandPanel.createPanel());
			panel.add(stylePanel.createPanel());
			panel.add(namePanel.createPanel());
			panel.add(colorPanel.createPanel());
			panel.add(sizePanel.createPanel(new String[] {"sizePanel"}));
			panel.add(articleTypePanel.createPanel(articleTypeDatabase.getArticleTypeProvider(), articleTypeDatabase.getNameOracle()));
			panel.add(notepadPanel.createPanel(notepadArticleProvider, true));
		}
		
		registerForEvents();
		
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

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(NotepadStartMaximizeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(NotepadStartMinimizeEvent.TYPE, this);
	}

}
