package com.xfashion.client.at;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.brand.BrandManagement;
import com.xfashion.client.at.category.CategoryManagement;
import com.xfashion.client.at.color.ColorManagement;
import com.xfashion.client.at.event.MaximizeAllFilterPanelsEvent;
import com.xfashion.client.at.event.MinimizeAllFilterPanelsEvent;
import com.xfashion.client.at.event.RefreshFilterEvent;
import com.xfashion.client.at.event.RefreshFilterHandler;
import com.xfashion.client.at.event.RequestShowArticleTypeDetailsByEanEvent;
import com.xfashion.client.at.event.RequestShowArticleTypeDetailsByEanHandler;
import com.xfashion.client.at.event.RequestShowArticleTypeDetailsEvent;
import com.xfashion.client.at.event.RequestShowArticleTypeDetailsHandler;
import com.xfashion.client.at.name.NamePanel;
import com.xfashion.client.at.popup.ArticleTypePopup;
import com.xfashion.client.at.popup.EditArticleTypePopup;
import com.xfashion.client.at.size.SizeManagement;
import com.xfashion.client.at.sort.DefaultArticleTypeComparator;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.notepad.NotepadPanel;
import com.xfashion.client.notepad.event.NotepadStartMaximizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMaximizeHandler;
import com.xfashion.client.notepad.event.NotepadStartMinimizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMinimizeHandler;
import com.xfashion.client.user.UserManagement;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.UserRole;

public class ArticleTypeView implements NotepadStartMaximizeHandler, NotepadStartMinimizeHandler, RefreshFilterHandler,
		RequestShowArticleTypeDetailsHandler, RequestShowArticleTypeDetailsByEanHandler {

	protected EventBus adminBus;
	
	protected HorizontalPanel panel;

	protected BrandManagement brandManagement;
	protected ColorManagement colorManagement;
	protected SizeManagement sizeManagement;
	protected CategoryManagement categoryManagement;
	
	protected NamePanel namePanel;
//	protected NotepadPanel notepadPanel;
	protected ArticleTypePanel articleTypePanel;

	protected ArticleTypePopup articleTypePopup = null;

	protected ArticleFilterProvider articleFilterProvider;
	protected DefaultArticleTypeComparator sortStrategy;

	public ArticleTypeView(ArticleFilterProvider articleFilterProvider, EventBus adminBus) {
		this.articleFilterProvider = articleFilterProvider;
		this.adminBus = adminBus;
		registerForEvents();
	}

	public BrandManagement getBrandManagement() {
		return brandManagement;
	}

	public void setBrandManagement(BrandManagement brandManagement) {
		this.brandManagement = brandManagement;
	}

	public ColorManagement getColorManagement() {
		return colorManagement;
	}

	public void setColorManagement(ColorManagement colorManagement) {
		this.colorManagement = colorManagement;
	}

	public SizeManagement getSizeManagement() {
		return sizeManagement;
	}

	public void setSizeManagement(SizeManagement sizeManagement) {
		this.sizeManagement = sizeManagement;
	}

	public CategoryManagement getCategoryManagement() {
		return categoryManagement;
	}

	public void setCategoryManagement(CategoryManagement categoryManagement) {
		this.categoryManagement = categoryManagement;
	}

	public Panel getPanel(ArticleAmountDataProvider notepadArticleProvider) {
		if (panel == null) {
			sortStrategy = new DefaultArticleTypeComparator();
			sortStrategy.setCategoryProvider(articleFilterProvider.getCategoryProvider());
			sortStrategy.setBrandProvider(articleFilterProvider.getBrandProvider());
			sortStrategy.setColorProvider(articleFilterProvider.getColorProvider());
			sortStrategy.setSizeProvider(articleFilterProvider.getSizeProvider());
			articleFilterProvider.getArticleTypeProvider().setSortStrategy(sortStrategy);

			panel = new HorizontalPanel();
			namePanel = new NamePanel(articleFilterProvider.getNameProvider(), adminBus);
			articleTypePanel = new ArticleTypePanel(articleFilterProvider);
//			notepadPanel = new NotepadPanel(articleFilterProvider);

			panel.add(categoryManagement.getCategoryAdminPanel());
			panel.add(brandManagement.getAdminPanel());
			panel.add(categoryManagement.getStyleAdminPanel());
			panel.add(namePanel.createPanel());
			panel.add(colorManagement.getAdminPanel());
			panel.add(sizeManagement.getAdminPanel());

			panel.add(articleTypePanel.createPanel(articleFilterProvider.getArticleTypeProvider(), articleFilterProvider.getNameProvider().getNameOracle()));
//			panel.add(notepadPanel.createPanel(notepadArticleProvider));
			adminBus.fireEvent(new RefreshFilterEvent());
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
	public void onRequestShowArticleTypeDetailsByEan(RequestShowArticleTypeDetailsByEanEvent event) {
		if (UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
			if (articleTypePopup == null) {
				articleTypePopup = new EditArticleTypePopup(articleFilterProvider);
			}
			ArticleTypeDTO articleType = articleFilterProvider.getArticleTypeProvider().retrieveArticleType(event.getEan());
			articleTypePopup.showPopup(articleType);
		}
	}

	@Override
	public void onNotepadStartMaximize(NotepadStartMaximizeEvent event) {
		if (panel != null) {
			adminBus.fireEvent(new MinimizeAllFilterPanelsEvent());
		}
	}

	@Override
	public void onNotepadStartMinimize(NotepadStartMinimizeEvent event) {
		if (panel != null) {
			adminBus.fireEvent(new MaximizeAllFilterPanelsEvent());
		}
	}

	private void registerForEvents() {
		adminBus.addHandler(NotepadStartMaximizeEvent.TYPE, this);
		adminBus.addHandler(NotepadStartMinimizeEvent.TYPE, this);
		adminBus.addHandler(RefreshFilterEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestShowArticleTypeDetailsEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestShowArticleTypeDetailsByEanEvent.TYPE, this);
	}

}
