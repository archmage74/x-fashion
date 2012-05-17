package com.xfashion.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.db.ArticleTypeService;
import com.xfashion.client.db.ArticleTypeServiceAsync;
import com.xfashion.client.db.RefreshFilterEvent;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.FilterCellData2;

public abstract class SimpleFilterDataProvider2<T extends FilterCellData2> extends FilterDataProvider2<T> {

	protected ArticleTypeServiceAsync articleTypeService = (ArticleTypeServiceAsync) GWT.create(ArticleTypeService.class);

	private Set<String> filter;

	public SimpleFilterDataProvider2(ArticleTypeDataProvider articleProvider) {
		super(articleProvider);
		filter = new HashSet<String>();
	}

	protected abstract void saveItem(T item);
	
	protected abstract void saveList();
	
	public Set<String> getFilter() {
		return filter;
	}

	protected void fireRefreshEvent() {
		Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
	}

	protected void moveDown(int idx) {
		if (idx < 0) {
			return;
		}
		if (idx + 1 >= getList().size()) {
			return;
		}
		T item = getList().remove(idx);
		getList().add(idx + 1, item);
		saveList();
	}

	@Override
	public List<ArticleTypeDTO> applyFilter(List<ArticleTypeDTO> articleTypes) {
		if (articleTypes == null) {
			articleTypes = new ArrayList<ArticleTypeDTO>();
		}
		ArrayList<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>();
		Set<String> filter = getFilter();

		if (filter != null && filter.size() > 0) {
			temp.clear();
			for (ArticleTypeDTO at : articleTypes) {
				if (filter.contains(getAttributeContent(at))) {
					temp.add(at);
				}
			}
			articleTypes.retainAll(temp);
		}

		return articleTypes;
	}

	public void update(List<ArticleTypeDTO> articleTypes) {
		HashMap<String, Integer> articleAmountPerAttribute = new HashMap<String, Integer>();
		for (ArticleTypeDTO at : articleTypes) {
			String attributeContent = this.getAttributeContent(at);
			if (attributeContent != null) {
				Integer availableArticles = articleAmountPerAttribute.get(attributeContent);
				if (availableArticles == null) {
					availableArticles = new Integer(1);
				} else {
					availableArticles++;
				}
				articleAmountPerAttribute.put(attributeContent, availableArticles);
			}
		}
		List<? extends FilterCellData2> cellDataList = getList();
		for (FilterCellData2 fcd : cellDataList) {
			Integer availableArticles = articleAmountPerAttribute.get(fcd.getKey());
			if (availableArticles == null) {
				availableArticles = 0;
			}
			fcd.setArticleAmount(availableArticles);
			fcd.setSelected(getFilter().contains(fcd.getKey()));
		}
		refresh();
	}

}
