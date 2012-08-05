package com.xfashion.client.at;

import com.google.gwt.user.client.ui.Panel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.brand.BrandManagement;
import com.xfashion.client.at.category.CategoryManagement;
import com.xfashion.client.at.color.ColorManagement;
import com.xfashion.client.at.event.RefreshFilterEvent;
import com.xfashion.client.at.name.NameDataProvider;
import com.xfashion.client.at.price.GetAtPriceFromArticleTypeStrategy;
import com.xfashion.client.at.price.GetDePriceFromArticleTypeStrategy;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.at.size.SizeManagement;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.user.LoginEvent;
import com.xfashion.client.user.LoginHandler;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.UserDTO;

public class ArticleTypeManagement implements LoginHandler {

	public static IGetPriceStrategy<ArticleTypeDTO> getArticleTypePriceStrategy;
	
	protected EventBus adminBus;
	
	protected Panel panel;
	protected ArticleTypeView articleTypeView;

	protected ArticleTypeDataProvider articleTypeProvider;
	protected ArticleFilterProvider articleFilterProvider;
	
	protected BrandManagement brandManagement;
	protected ColorManagement colorManagement;
	protected SizeManagement sizeManagement;
	protected CategoryManagement categoryManagement;
	
	public ArticleTypeManagement() {
		this.adminBus = new SimpleEventBus();
		this.articleTypeProvider = new ArticleTypeDataProvider();
		
		this.brandManagement = new BrandManagement(articleTypeProvider, adminBus);
		this.colorManagement = new ColorManagement(articleTypeProvider, adminBus);
		this.sizeManagement = new SizeManagement(articleTypeProvider, adminBus);
		this.categoryManagement = new CategoryManagement(articleTypeProvider, adminBus);

		this.articleFilterProvider = new ArticleFilterProvider(articleTypeProvider);

		this.articleFilterProvider.setCategoryProvider(categoryManagement.getCategoryProvider());
		this.articleFilterProvider.setBrandProvider(brandManagement.getBrandProvider());
		this.articleFilterProvider.setColorProvider(colorManagement.getColorProvider());
		this.articleFilterProvider.setSizeProvider(sizeManagement.getSizeProvider());

		NameDataProvider nameProvider = new NameDataProvider(articleTypeProvider, articleFilterProvider, adminBus);
		this.articleFilterProvider.setNameProvider(nameProvider);

		this.articleTypeView = new ArticleTypeView(articleFilterProvider, adminBus);
		this.articleTypeView.setCategoryManagement(categoryManagement);
		this.articleTypeView.setBrandManagement(brandManagement);
		this.articleTypeView.setColorManagement(colorManagement);
		this.articleTypeView.setSizeManagement(sizeManagement);

		registerForEvents();
	}

	public ArticleFilterProvider getArticleFilterProvider() {
		return articleFilterProvider;
	}

	public BrandManagement getBrandManagement() {
		return brandManagement;
	}

	public ColorManagement getColorManagement() {
		return colorManagement;
	}

	public SizeManagement getSizeManagement() {
		return sizeManagement;
	}

	public CategoryManagement getCategoryManagement() {
		return categoryManagement;
	}

	public void init() {
		articleTypeProvider.readArticleTypes();
		brandManagement.init();
		colorManagement.init();
		sizeManagement.init();
		categoryManagement.init();
		articleFilterProvider.init();
	}

	public Panel getPanel(ArticleAmountDataProvider notepadArticleProvider) {
		if (panel == null) {
			panel = articleTypeView.getPanel(notepadArticleProvider);
		}

		Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
		return panel;
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
		Xfashion.eventBus.addHandler(LoginEvent.TYPE, this);
	}

}
