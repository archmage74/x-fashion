package com.xfashion.client.at;

import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.event.RefreshFilterEvent;
import com.xfashion.client.at.price.GetAtPriceFromArticleTypeStrategy;
import com.xfashion.client.at.price.GetDePriceFromArticleTypeStrategy;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.user.LoginEvent;
import com.xfashion.client.user.LoginHandler;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.UserDTO;

public class ArticleTypeManagement implements LoginHandler {

	public static IGetPriceStrategy<ArticleTypeDTO> getArticleTypePriceStrategy;
	
	protected Panel panel;
	protected ArticleTypeView articleTypeView;

	protected ArticleFilterProvider articleFilterProvider;
	
	public ArticleTypeManagement() {
		ArticleTypeDataProvider articleTypeProvider = new ArticleTypeDataProvider();
		this.articleFilterProvider = new ArticleFilterProvider(articleTypeProvider);
		this.articleTypeView = new ArticleTypeView(articleFilterProvider);
		registerForEvents();
	}

	public ArticleFilterProvider getArticleFilterProvider() {
		return articleFilterProvider;
	}

	public void init() {
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
