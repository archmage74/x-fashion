package com.xfashion.client.cat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.FilterCellData;
import com.xfashion.shared.StyleDTO;

public class CategoryDataProvider extends FilterDataProvider<CategoryDTO>{

	protected CategoryDTO categoryFilter = null;
	
	protected Set<String> styleFilter;
	
	protected Map<String, StyleDTO> styles;
	
	protected ListDataProvider<StyleDTO> styleProvider;
	
	public CategoryDataProvider() {
		super();
		styleProvider = new ListDataProvider<StyleDTO>();
		styleFilter = new HashSet<String>();
		styles = new HashMap<String, StyleDTO>();
	}
	
	@Override
	protected Long getAttributeContent(ArticleTypeDTO articleType) {
		articleType.getCategoryId();
		return null;
	}

	public CategoryDTO getCategoryFilter() {
		return categoryFilter;
	}
	
	public void setCategoryFilter(CategoryDTO categoryFilter) {
		this.categoryFilter = categoryFilter;
	}
	
	public void updateStyles(List<ArticleTypeDTO> articleTypes) {
		if (categoryFilter != null) {
			styleProvider.setList(new ArrayList<StyleDTO>(categoryFilter.getStyles()));
			HashMap<String, Integer> articleAmountPerAttribute = new HashMap<String, Integer>();
			for (ArticleTypeDTO at : articleTypes) {
				String styleId = at.getStyleId();
				if (styleId != null) {
					Integer availableArticles = articleAmountPerAttribute.get(styleId);
					if (availableArticles == null) {
						availableArticles = new Integer(1);
					} else {
						availableArticles++;
					}
					articleAmountPerAttribute.put(styleId, availableArticles);
				}
			}
			List<? extends FilterCellData<?>> cellDataList = categoryFilter.getStyles();
			for (FilterCellData<?> scd : cellDataList) {
				Integer availableArticles = articleAmountPerAttribute.get(scd.getId());
				if (availableArticles == null) {
					availableArticles = 0;
				}
				scd.setArticleAmount(availableArticles);
				scd.setSelected(styleFilter.contains(scd.getId()));
			}
			refresh();
		} else {
			styleProvider.getList().clear();
		}
	}
	
	public Set<String> getStyleFilter() {
		return styleFilter;
	}

	public void setStyleFilter(Set<String> styleFilter) {
		this.styleFilter = styleFilter;
	}
	
	public StyleDTO resolveStyle(String styleId) {
		return styles.get(styleId);
	}

	public ListDataProvider<StyleDTO> getStyleProvider() {
		return styleProvider;
	}

	public Long freeCatgegoryId() {
		Set<Long> ids = new HashSet<Long>();
		for (long id = 1L; id<100L; id++) {
			ids.add(id);
		}
		for (CategoryDTO c : getList()) {
			ids.remove(c.getId());
		}
		return ids.iterator().next(); 
	}

	@Override
	public List<ArticleTypeDTO> applyFilter(List<ArticleTypeDTO> articleTypes) {
		if (articleTypes == null) {
			articleTypes = new ArrayList<ArticleTypeDTO>();
		}
		List<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>(articleTypes);

		result = applyCategoryFilter(result);
		result = applyStyleFilter(result);
		return result;
	}
	
	public List<ArticleTypeDTO> applyCategoryFilter(List<ArticleTypeDTO> articleTypes) {
		if (categoryFilter != null) {
			ArrayList<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>();
			for (ArticleTypeDTO at : articleTypes) {
				if (categoryFilter.getId().equals(at.getCategoryId())) {
					temp.add(at);
				}
			}
			articleTypes.retainAll(temp);
		}
		
		return articleTypes;
	}

	public List<ArticleTypeDTO> applyStyleFilter(List<ArticleTypeDTO> articleTypes) {
		if (categoryFilter != null) {
			ArrayList<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>();

			if (styleFilter != null && styleFilter.size() > 0) {
				temp.clear();
				for (ArticleTypeDTO at : articleTypes) {
					if (styleFilter.contains(at.getStyleId())) {
						temp.add(at);
					}
				}
				articleTypes.retainAll(temp);
			}
		}
		return articleTypes;
	}

	@Override
	public void refresh() {
		super.refresh();
		// updateStyles(articles);
	}
	
	@Override
	protected void refreshResolver(List<CategoryDTO> list) {
		super.refreshResolver(list);
		for (CategoryDTO dto : list) {
			for (StyleDTO s : dto.getStyles()) {
				styles.put(s.getId(), s);
			}
		}
	}
}
