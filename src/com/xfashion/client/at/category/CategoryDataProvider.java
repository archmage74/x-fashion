package com.xfashion.client.at.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.category.event.CategoriesLoadedEvent;
import com.xfashion.client.at.category.event.SelectCategoryEvent;
import com.xfashion.client.at.category.event.SelectCategoryHandler;
import com.xfashion.client.at.style.StyleDataProvider;
import com.xfashion.client.at.style.event.ClearStyleSelectionEvent;
import com.xfashion.client.at.style.event.ClearStyleSelectionHandler;
import com.xfashion.client.at.style.event.SelectStyleEvent;
import com.xfashion.client.at.style.event.SelectStyleHandler;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.FilterCellData;
import com.xfashion.shared.StyleDTO;

public class CategoryDataProvider extends FilterDataProvider<CategoryDTO> implements SelectStyleHandler, ClearStyleSelectionHandler,
		SelectCategoryHandler {

	protected Map<String, StyleDTO> styles;

	protected StyleDataProvider styleProvider;

	public CategoryDataProvider(ArticleTypeDataProvider articleTypeProvider, EventBus eventBus) {
		super(articleTypeProvider, eventBus);
		
		this.styleProvider = new StyleDataProvider(articleTypeProvider, eventBus);
		this.styles = new HashMap<String, StyleDTO>();
		
		registerForEvents();
	}

	@Override
	protected String getAttributeContent(ArticleTypeDTO articleType) {
		articleType.getCategoryKey();
		return null;
	}

	public CategoryDTO getCategoryFilter() {
		if (getFilter().size() > 0) {
			return idToItem.get(getFilter().iterator().next());
		} else {
			return null; 
		}
	}

	public void updateStyles(List<ArticleTypeDTO> articleTypes) {
		updateStyles(articleTypes, null);
	}

	public void updateStyles(List<ArticleTypeDTO> articleTypes, Map<String, ArticleAmountDTO> articleAmounts) {
		CategoryDTO categoryFilter = getCategoryFilter(); 
		if (categoryFilter != null) {
			styleProvider.setAllItems(new ArrayList<StyleDTO>(categoryFilter.getStyles()));
			HashMap<String, Integer> articleAmountPerAttribute = new HashMap<String, Integer>();
			for (ArticleTypeDTO at : articleTypes) {
				String styleId = at.getStyleKey();
				if (styleId != null) {
					Integer availableArticles = articleAmountPerAttribute.get(styleId);
					if (availableArticles == null) {
						availableArticles = new Integer(0);
					}
					if (articleAmounts != null) {
						ArticleAmountDTO articleAmount = articleAmounts.get(at.getKey());
						if (articleAmount != null) {
							availableArticles += articleAmount.getAmount();
						} else {
							availableArticles += 0;
						}
					} else {
						availableArticles++;
					}
					articleAmountPerAttribute.put(styleId, availableArticles);
				}
			}
			List<? extends FilterCellData> cellDataList = categoryFilter.getStyles();
			for (FilterCellData scd : cellDataList) {
				Integer availableArticles = articleAmountPerAttribute.get(scd.getKey());
				if (availableArticles == null) {
					availableArticles = 0;
				}
				scd.setArticleAmount(availableArticles);
			}
			refresh();
			styleProvider.refresh();
		} else {
			styleProvider.clearAllItems();
		}
	}

	public Set<String> getStyleFilter() {
		return styleProvider.getFilter();
	}

	public StyleDTO resolveStyle(String styleId) {
		return styles.get(styleId);
	}

	public StyleDataProvider getStyleProvider() {
		return styleProvider;
	}

	public Long freeCatgegoryId() {
		Set<Long> ids = new HashSet<Long>();
		for (long id = 1L; id < 100L; id++) {
			ids.add(id);
		}
		for (CategoryDTO c : getAllItems()) {
			ids.remove(c.getKey());
		}
		return ids.iterator().next();
	}

	@Override
	public List<ArticleTypeDTO> applyFilter(List<ArticleTypeDTO> articleTypes) {
		if (articleTypes == null) {
			return new ArrayList<ArticleTypeDTO>();
		}
		applyCategoryFilter(articleTypes);
		applyStyleFilter(articleTypes);
		return articleTypes;
	}

	public List<ArticleTypeDTO> applyCategoryFilter(List<ArticleTypeDTO> articleTypes) {
		CategoryDTO categoryFilter = getCategoryFilter();
		if (categoryFilter != null) {
			ArrayList<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>();
			for (ArticleTypeDTO at : articleTypes) {
				if (categoryFilter.getKey().equals(at.getCategoryKey())) {
					temp.add(at);
				}
			}
			articleTypes.retainAll(temp);
		}
		return articleTypes;
	}

	public List<ArticleTypeDTO> applyStyleFilter(List<ArticleTypeDTO> articleTypes) {
		if (getCategoryFilter() != null) {
			ArrayList<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>();

			if (getStyleFilter() != null && getStyleFilter().size() > 0) {
				temp.clear();
				for (ArticleTypeDTO at : articleTypes) {
					if (getStyleFilter().contains(at.getStyleKey())) {
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
	}

	@Override
	protected void refreshResolver(List<CategoryDTO> list) {
		super.refreshResolver(list);
		for (CategoryDTO dto : list) {
			for (StyleDTO s : dto.getStyles()) {
				styles.put(s.getKey(), s);
			}
		}
	}

	public void saveItem(final CategoryDTO category) {
		AsyncCallback<CategoryDTO> callback = new AsyncCallback<CategoryDTO>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(CategoryDTO result) {
				category.getStyles().clear();
				category.getStyles().addAll(result.getStyles());
				fireRefreshEvent();
			}
		};
		articleTypeService.updateCategory(category, callback);
	}

	public void saveList() {
		AsyncCallback<List<CategoryDTO>> callback = new AsyncCallback<List<CategoryDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(List<CategoryDTO> result) {
				storeCategories(result);
			}
		};
		articleTypeService.updateCategories(new ArrayList<CategoryDTO>(getAllItems()), callback);
	}

	public boolean doesCategoryHaveArticles(CategoryDTO category) {
		for (ArticleTypeDTO at : articleTypeProvider.getList()) {
			if (at.getCategoryKey().equals(category.getKey())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onSelectCategory(SelectCategoryEvent event) {
		getFilter().clear();
		CategoryDTO dto = event.getCellData();
		if (dto != null) {
			getFilter().add(dto.getKey());
		}
		clearStyleSelection();
		fireRefreshEvent();
	}

	public void moveDownCategory(int idx) {
		if (idx < 0) {
			return;
		}
		if (idx + 1 >= getAllItems().size()) {
			return;
		}
		CategoryDTO item = getAllItems().remove(idx);
		getAllItems().add(idx + 1, item);
		saveList();
	}

	@Override
	public void onSelectStyle(SelectStyleEvent event) {
		StyleDTO dto = event.getCellData();
		boolean isSelected = getStyleFilter().contains(dto.getKey());
		if (isSelected) {
			getStyleFilter().remove(dto.getKey());
		} else {
			getStyleFilter().add(dto.getKey());
		}
		fireRefreshEvent();
	}

	@Override
	public void onClearStyleSelection(ClearStyleSelectionEvent event) {
		clearStyleSelection();
		fireRefreshEvent();
	}

	public void readCategories() {
		AsyncCallback<List<CategoryDTO>> callback = new AsyncCallback<List<CategoryDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(List<CategoryDTO> result) {
				storeCategories(result);
			}
		};
		articleTypeService.readCategories(callback);
	}

	private void clearStyleSelection() {
		getStyleFilter().clear();
	}

	private void registerForEvents() {
		eventBus.addHandler(SelectCategoryEvent.TYPE, this);
		eventBus.addHandler(SelectStyleEvent.TYPE, this);
		eventBus.addHandler(ClearStyleSelectionEvent.TYPE, this);
	}

	private void storeCategories(List<CategoryDTO> result) {
		setAllItems(result);
		refreshResolver();
		fireRefreshEvent();
		eventBus.fireEvent(new CategoriesLoadedEvent());
	}

}
