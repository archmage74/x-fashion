package com.xfashion.client.at;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.brand.BrandPanel;
import com.xfashion.client.cat.CategoryPanel;
import com.xfashion.client.color.ColorPanel;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.db.event.RefreshFilterEvent;
import com.xfashion.client.name.NamePanel;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.notepad.NotepadPanel;
import com.xfashion.client.notepad.event.NotepadStartMaximizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMaximizeHandler;
import com.xfashion.client.notepad.event.NotepadStartMinimizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMinimizeHandler;
import com.xfashion.client.size.SizePanel;
import com.xfashion.client.style.StylePanel;
import com.xfashion.client.user.LoginEvent;
import com.xfashion.client.user.LoginHandler;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.UserDTO;

public class ArticleTypeManagement implements NotepadStartMaximizeHandler, NotepadStartMinimizeHandler, LoginHandler {

	public static IGetPriceStrategy<ArticleTypeDTO> getArticleTypePriceStrategy;

	protected HorizontalPanel panel;
	
	protected CategoryPanel categoryPanel;
	protected BrandPanel brandPanel;
	protected StylePanel stylePanel;
	protected SizePanel sizePanel;
	protected ColorPanel colorPanel;
	protected NamePanel namePanel;
	protected NotepadPanel notepadPanel;
	protected ArticleTypePanel articleTypePanel;

	public ArticleTypeManagement() {
		registerForEvents();
	}
	
	public Panel getPanel(ArticleTypeDatabase articleTypeDatabase, ArticleAmountDataProvider notepadArticleProvider) {
		if (panel == null) {
			panel = new HorizontalPanel();
			
			categoryPanel = new CategoryPanel(articleTypeDatabase.getCategoryProvider());
			brandPanel = new BrandPanel(articleTypeDatabase.getBrandProvider());
			stylePanel = new StylePanel(articleTypeDatabase.getCategoryProvider().getStyleProvider());
			sizePanel = new SizePanel(articleTypeDatabase.getSizeProvider());
			colorPanel = new ColorPanel(articleTypeDatabase.getColorProvider());
			namePanel = new NamePanel(articleTypeDatabase.getNameProvider());
			articleTypePanel = new ArticleTypePanel(articleTypeDatabase);
			notepadPanel = new NotepadPanel(articleTypeDatabase);
			
			panel.add(categoryPanel.createPanel());
			panel.add(brandPanel.createPanel());
			panel.add(stylePanel.createPanel());
			panel.add(namePanel.createPanel());
			panel.add(colorPanel.createPanel());
			panel.add(sizePanel.createPanel());
			panel.add(articleTypePanel.createPanel(articleTypeDatabase.getArticleTypeProvider(), articleTypeDatabase.getNameOracle()));
			panel.add(notepadPanel.createPanel(notepadArticleProvider, true));
		}
		
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

	@Override
	public void onLogin(LoginEvent event) {
		UserDTO user = event.getUser();
		switch (user.getCountry()) {
		case AT:
			ArticleTypeManagement.getArticleTypePriceStrategy = new GetAtPriceFromArticleTypeStrategy();
			break;
		case DE:
			ArticleTypeManagement.getArticleTypePriceStrategy = new GetDePriceFromArticleTypeStrategy();
			break;
		}
		Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
	}
	
	private void registerForEvents() {
		Xfashion.eventBus.addHandler(NotepadStartMaximizeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(NotepadStartMinimizeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(LoginEvent.TYPE, this);
	}

}
