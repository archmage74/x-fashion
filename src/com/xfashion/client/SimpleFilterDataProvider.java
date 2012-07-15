package com.xfashion.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.FilterCellData;

public abstract class SimpleFilterDataProvider<T extends FilterCellData> extends FilterDataProvider<T> {

	private Set<String> filter;

	public SimpleFilterDataProvider(ArticleTypeDataProvider articleProvider) {
		super(articleProvider);
		filter = new HashSet<String>();
	}

	protected abstract void saveItem(T item);
	
	protected abstract void saveList();
	
	public Set<String> getFilter() {
		return filter;
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

	public void update(List<ArticleTypeDTO> articleTypes, HashMap<String, ArticleAmountDTO> articleAmounts) {
		HashMap<String, Integer> articleAmountPerAttribute = new HashMap<String, Integer>();
		for (ArticleTypeDTO at : articleTypes) {
			int amount = 1;
			if (articleAmounts != null) {
				ArticleAmountDTO articleAmount = articleAmounts.get(at.getKey());
				if (articleAmount != null) {
					amount = articleAmount.getAmount();
				} else {
					amount = 0;
				}
			}
			increaseAmountCounter(articleAmountPerAttribute, at, amount);
		}
		updateCellDataList(articleAmountPerAttribute);
		refresh();
	}
	
	private void updateCellDataList(HashMap<String, Integer> articleAmountPerAttribute) {
		List<? extends FilterCellData> cellDataList = getList();
		for (FilterCellData fcd : cellDataList) {
			Integer availableArticles = articleAmountPerAttribute.get(fcd.getKey());
			if (availableArticles == null) {
				availableArticles = 0;
			}
			fcd.setArticleAmount(availableArticles);
			fcd.setSelected(getFilter().contains(fcd.getKey()));
		}
	}

	private void increaseAmountCounter(HashMap<String, Integer> articleAmountPerAttribute, ArticleTypeDTO articleType, int amount) {
		String attributeContent = this.getAttributeContent(articleType);
		if (attributeContent != null) {
			Integer availableArticles = articleAmountPerAttribute.get(attributeContent);
			if (availableArticles == null) {
				availableArticles = new Integer(0);
			}
			availableArticles += amount;
			articleAmountPerAttribute.put(attributeContent, availableArticles);
		}
	}

}
