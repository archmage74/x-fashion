package com.xfashion.client.at;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.brand.BrandEditor;
import com.xfashion.client.at.brand.BrandPanel;
import com.xfashion.client.at.category.CategoryEditor;
import com.xfashion.client.at.category.CategoryPanel;
import com.xfashion.client.at.color.ColorEditor;
import com.xfashion.client.at.color.ColorPanel;
import com.xfashion.client.at.event.RefreshFilterEvent;
import com.xfashion.client.at.event.RefreshFilterHandler;
import com.xfashion.client.at.event.RequestShowArticleTypeDetailsEvent;
import com.xfashion.client.at.event.RequestShowArticleTypeDetailsHandler;
import com.xfashion.client.at.name.NamePanel;
import com.xfashion.client.at.popup.ArticleTypePopup;
import com.xfashion.client.at.popup.EditArticleTypePopup;
import com.xfashion.client.at.size.SizeEditor;
import com.xfashion.client.at.size.SizePanel;
import com.xfashion.client.at.sort.DefaultArticleTypeComparator;
import com.xfashion.client.at.style.StyleEditor;
import com.xfashion.client.at.style.StylePanel;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.notepad.NotepadPanel;
import com.xfashion.client.notepad.event.NotepadStartMaximizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMaximizeHandler;
import com.xfashion.client.notepad.event.NotepadStartMinimizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMinimizeHandler;
import com.xfashion.client.user.UserManagement;
import com.xfashion.shared.UserRole;

public class ArticleTypeView implements NotepadStartMaximizeHandler, NotepadStartMinimizeHandler, RefreshFilterHandler,
		RequestShowArticleTypeDetailsHandler {

	protected HorizontalPanel panel;

	protected CategoryPanel categoryPanel;
	protected BrandPanel brandPanel;
	protected StylePanel stylePanel;
	protected SizePanel sizePanel;
	protected ColorPanel colorPanel;
	protected NamePanel namePanel;
	protected NotepadPanel notepadPanel;
	protected ArticleTypePanel articleTypePanel;

	protected ArticleTypePopup articleTypePopup = null;

	protected ArticleFilterProvider articleFilterProvider;
	protected DefaultArticleTypeComparator sortStrategy;

	public ArticleTypeView(ArticleFilterProvider articleFilterProvider) {
		this.articleFilterProvider = articleFilterProvider;
		registerForEvents();
	}

	public Panel getPanel(ArticleAmountDataProvider notepadArticleProvider) {
		if (panel == null) {
			panel = new HorizontalPanel();
			EventBus eventBus = articleFilterProvider.getFilterEventBus(); 
			categoryPanel = new CategoryPanel(articleFilterProvider.getCategoryProvider(), eventBus);
			brandPanel = new BrandPanel(articleFilterProvider.getBrandProvider(), eventBus);
			stylePanel = new StylePanel(articleFilterProvider.getCategoryProvider().getStyleProvider(), eventBus);
			sizePanel = new SizePanel(articleFilterProvider.getSizeProvider(), eventBus);
			colorPanel = new ColorPanel(articleFilterProvider.getColorProvider(), eventBus);
			namePanel = new NamePanel(articleFilterProvider.getNameProvider(), eventBus);
			articleTypePanel = new ArticleTypePanel(articleFilterProvider);
			notepadPanel = new NotepadPanel(articleFilterProvider);

			sortStrategy = new DefaultArticleTypeComparator();
			sortStrategy.setCategoryProvider(articleFilterProvider.getCategoryProvider());
			sortStrategy.setBrandProvider(articleFilterProvider.getBrandProvider());
			sortStrategy.setColorProvider(articleFilterProvider.getColorProvider());
			sortStrategy.setSizeProvider(articleFilterProvider.getSizeProvider());
			articleFilterProvider.getArticleTypeProvider().setSortStrategy(sortStrategy);

			if (UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
				new CategoryEditor(categoryPanel);
				new StyleEditor(stylePanel);
				new BrandEditor(brandPanel);
				new ColorEditor(colorPanel);
				new SizeEditor(sizePanel);
			}
			
			panel.add(categoryPanel.createPanel());
			panel.add(brandPanel.createPanel());
			panel.add(stylePanel.createPanel());
			panel.add(namePanel.createPanel());
			panel.add(colorPanel.createPanel());
			panel.add(sizePanel.createPanel());
			panel.add(articleTypePanel.createPanel(articleFilterProvider.getArticleTypeProvider(), articleFilterProvider.getNameProvider().getNameOracle()));
			panel.add(notepadPanel.createPanel(notepadArticleProvider, true));
		}
		return panel;
	}

	@Override
	public void onRefreshFilter(RefreshFilterEvent event) {
		articleFilterProvider.getArticleTypeProvider().applyFilters(articleFilterProvider);
		articleFilterProvider.updateProviders();
	}

	@Override
	public void onRequestShowArticleTypeDetails(RequestShowArticleTypeDetailsEvent event) {
		if (UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
			if (articleTypePopup == null) {
				articleTypePopup = new EditArticleTypePopup(articleFilterProvider);
			}
			articleTypePopup.showPopup(event.getArticleType());
		}
	}

	@Override
	public void onNotepadStartMaximize(NotepadStartMaximizeEvent event) {
		if (panel != null) {
			brandPanel.minimize();
			stylePanel.minimize();
			sizePanel.minimize();
			colorPanel.minimize();
			namePanel.minimize();
		}
	}

	@Override
	public void onNotepadStartMinimize(NotepadStartMinimizeEvent event) {
		if (panel != null) {
			brandPanel.maximize();
			stylePanel.maximize();
			sizePanel.maximize();
			colorPanel.maximize();
			namePanel.maximize();
		}
	}

	private void registerForEvents() {
		EventBus eventBus = articleFilterProvider.getFilterEventBus();
		eventBus.addHandler(NotepadStartMaximizeEvent.TYPE, this);
		eventBus.addHandler(NotepadStartMinimizeEvent.TYPE, this);
		eventBus.addHandler(RefreshFilterEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestShowArticleTypeDetailsEvent.TYPE, this);
	}

}
