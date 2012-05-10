package com.xfashion.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.FilterCellData;

public abstract class SimpleFilterDataProvider<T extends FilterCellData<?>> extends FilterDataProvider<T> {

	private Set<Long> filter;
	
	public SimpleFilterDataProvider() {
		super();
		filter = new HashSet<Long>();
	}
	
	public Set<Long> getFilter() {
		return filter;
	}

	public void setFilter(Set<Long> filter) {
		this.filter = filter;
	}
	
	@Override
	public List<ArticleTypeDTO> applyFilter(List<ArticleTypeDTO> articleTypes) {
		if (articleTypes == null) {
			articleTypes = new ArrayList<ArticleTypeDTO>();
		}
		ArrayList<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>();
		Set<Long> filter = getFilter();

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
		HashMap<Long, Integer> articleAmountPerAttribute = new HashMap<Long, Integer>();
		for (ArticleTypeDTO at : articleTypes) {
			Long attributeContent = this.getAttributeContent(at);
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
		List<? extends FilterCellData<?>> cellDataList = getList();
		for (FilterCellData<?> scd : cellDataList) {
			Integer availableArticles = articleAmountPerAttribute.get(scd.getId());
			if (availableArticles == null) {
				availableArticles = 0;
			}
			scd.setArticleAmount(availableArticles);
			scd.setSelected(getFilter().contains(scd.getId()));
		}
		refresh();
	}

}
