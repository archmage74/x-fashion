package com.xfashion.client.at.name;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.view.client.ListDataProvider;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.at.category.event.SelectCategoryEvent;
import com.xfashion.client.at.category.event.SelectCategoryHandler;
import com.xfashion.client.at.event.ArticlesLoadedEvent;
import com.xfashion.client.at.event.ArticlesLoadedHandler;
import com.xfashion.client.at.event.RefreshFilterEvent;
import com.xfashion.client.at.name.event.NameFilterEvent;
import com.xfashion.client.at.name.event.NameFilterHandler;
import com.xfashion.shared.ArticleTypeDTO;

public class NameDataProvider extends ListDataProvider<String> implements NameFilterHandler, ArticlesLoadedHandler, SelectCategoryHandler {

	protected String selectedName = null;
	
	protected EventBus eventBus;
	
	protected MultiWordSuggestOracle nameOracle;

	protected IProvideArticleFilter filterProvider;
	protected ArticleTypeDataProvider articleTypeProvider;
	
	public NameDataProvider(ArticleTypeDataProvider articleTypeProvider, IProvideArticleFilter filterProvider, EventBus eventBus) {
		this.articleTypeProvider = articleTypeProvider;
		this.filterProvider = filterProvider;
		this.eventBus = eventBus;
		
		this.nameOracle = new MultiWordSuggestOracle();
		
		registerForEvents();
	}
	
	public String getSelectedName() {
		return selectedName;
	}
	
	public MultiWordSuggestOracle getNameOracle() {
		return nameOracle;
	}

	public void updateAvailableArticleNames() {
		List<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>(articleTypeProvider.getAllArticleTypes());
		result = filterProvider.getCategoryProvider().applyFilter(result);
		result = filterProvider.getBrandProvider().applyFilter(result);
		result = filterProvider.getSizeProvider().applyFilter(result);
		result = filterProvider.getColorProvider().applyFilter(result);
		
		List<String> filteredNames = getFilteredNames(result);
		Collection<String> availableNames = articleTypeProvider.getVisibleArticleNames();
		filteredNames.retainAll(availableNames);
		
		Collections.sort(filteredNames);
		setList(filteredNames);
		nameOracle.clear();
		nameOracle.addAll(filteredNames);
	}

	@Override
	public void onNameFilter(NameFilterEvent event) {
		selectedName = event.getName();
		eventBus.fireEvent(new RefreshFilterEvent());
	}

	@Override
	public void onSelectCategory(SelectCategoryEvent event) {
		selectedName = null;
	}
	
	@Override
	public void onArticlesLoaded(ArticlesLoadedEvent event) {
		updateAvailableArticleNames();
	}
	
	public List<ArticleTypeDTO> applyFilter(List<ArticleTypeDTO> articleTypes) {
		if (articleTypes == null) {
			articleTypes = new ArrayList<ArticleTypeDTO>();
			return articleTypes;
		}
		if (selectedName == null) {
			return articleTypes;
		}

		ArrayList<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>();
		for (ArticleTypeDTO at : articleTypes) {
			if (selectedName.equals(at.getName())) {
				temp.add(at);
			}
		}
		articleTypes.retainAll(temp);

		return articleTypes;
	}

	private List<String> getFilteredNames(List<ArticleTypeDTO> articleTypes) {
		Set<String> names = new HashSet<String>();
		for (ArticleTypeDTO at : articleTypes) {
			names.add(at.getName());
		}
		return new ArrayList<String>(names);
	}

	private void registerForEvents() {
		eventBus.addHandler(NameFilterEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ArticlesLoadedEvent.TYPE, this);
		eventBus.addHandler(SelectCategoryEvent.TYPE, this);
	}
		
}
