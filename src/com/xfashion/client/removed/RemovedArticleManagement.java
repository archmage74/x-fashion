package com.xfashion.client.removed;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleFilterProvider;
import com.xfashion.client.removed.event.AddMoreRemovedArticlesEvent;
import com.xfashion.client.removed.event.AddMoreRemovedArticlesHandler;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.RemovedArticleDTO;

public class RemovedArticleManagement implements AddMoreRemovedArticlesHandler {
	
	public static final int CHUNK_SIZE = 50;
	
	private UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);

	private RemovedArticlePanel removedArticlePanel;
	private Panel panel;
	
	protected ArticleFilterProvider articleFilterProvider;
	private RemovedArticleDataProvider removedArticleProvider;
	
	TextMessages textMessages;

	
	public RemovedArticleManagement(ArticleFilterProvider articleFilterProvider) {
		this.textMessages = GWT.create(TextMessages.class);
		this.articleFilterProvider = articleFilterProvider;
		this.removedArticleProvider = new RemovedArticleDataProvider(articleFilterProvider.getArticleTypeProvider());

		this.removedArticlePanel = new RemovedArticlePanel(articleFilterProvider);

		registerForEvents();
	}
	
	public Panel getPanel() {
		if (panel == null) {
			panel = removedArticlePanel.createPanel(removedArticleProvider);
		}
		refresh();
		return panel;
	}
	
	@Override
	public void onAddMoreRemovedArticles(AddMoreRemovedArticlesEvent event) {
		addMoreRemovedArticles();
	}
	
	private void addMoreRemovedArticles() {
		int from = removedArticleProvider.getList().size();
		int to = from + CHUNK_SIZE;
		AsyncCallback<List<RemovedArticleDTO>> callback = new AsyncCallback<List<RemovedArticleDTO>>() {
			@Override
			public void onSuccess(List<RemovedArticleDTO> result) {
				addRemovedArticles(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		userService.readOwnRemovedArticles(from, to, callback);
	}
	
	private void refresh() {
		readRemovedArticles();
	}
	
	private void readRemovedArticles() {
		clearRemovedArticles();
		addMoreRemovedArticles();
	}
	
	private void clearRemovedArticles() {
		removedArticleProvider.getList().clear();
		removedArticlePanel.enableAddMoreRemovedArticles();
	}
	
	private void addRemovedArticles(List<RemovedArticleDTO> result) {
		removedArticleProvider.getList().addAll(result);
		if (result.size() < CHUNK_SIZE) {
			removedArticlePanel.disableAddMoreRemovedArticles();
		}
	}
	
	private void registerForEvents() {
		Xfashion.eventBus.addHandler(AddMoreRemovedArticlesEvent.TYPE, this);
	}

}
