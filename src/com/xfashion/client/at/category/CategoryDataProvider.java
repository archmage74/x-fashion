package com.xfashion.client.at.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.client.ErrorEvent;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.category.event.CategoriesLoadedEvent;
import com.xfashion.client.at.category.event.CreateCategoryEvent;
import com.xfashion.client.at.category.event.CreateCategoryHandler;
import com.xfashion.client.at.category.event.DeleteCategoryEvent;
import com.xfashion.client.at.category.event.DeleteCategoryHandler;
import com.xfashion.client.at.category.event.MoveDownCategoryEvent;
import com.xfashion.client.at.category.event.MoveDownCategoryHandler;
import com.xfashion.client.at.category.event.MoveUpCategoryEvent;
import com.xfashion.client.at.category.event.MoveUpCategoryHandler;
import com.xfashion.client.at.category.event.SelectCategoryEvent;
import com.xfashion.client.at.category.event.SelectCategoryHandler;
import com.xfashion.client.at.category.event.ShowChooseCategoryAndStylePopupEvent;
import com.xfashion.client.at.category.event.ShowChooseCategoryAndStylePopupHandler;
import com.xfashion.client.at.category.event.UpdateCategoryEvent;
import com.xfashion.client.at.category.event.UpdateCategoryHandler;
import com.xfashion.client.at.name.NameFilterEvent;
import com.xfashion.client.at.style.StyleDataProvider;
import com.xfashion.client.at.style.event.ClearStyleSelectionEvent;
import com.xfashion.client.at.style.event.ClearStyleSelectionHandler;
import com.xfashion.client.at.style.event.CreateStyleEvent;
import com.xfashion.client.at.style.event.CreateStyleHandler;
import com.xfashion.client.at.style.event.DeleteStyleEvent;
import com.xfashion.client.at.style.event.DeleteStyleHandler;
import com.xfashion.client.at.style.event.MoveDownStyleEvent;
import com.xfashion.client.at.style.event.MoveDownStyleHandler;
import com.xfashion.client.at.style.event.MoveUpStyleEvent;
import com.xfashion.client.at.style.event.MoveUpStyleHandler;
import com.xfashion.client.at.style.event.SelectStyleEvent;
import com.xfashion.client.at.style.event.SelectStyleHandler;
import com.xfashion.client.at.style.event.UpdateStyleEvent;
import com.xfashion.client.at.style.event.UpdateStyleHandler;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.FilterCellData;
import com.xfashion.shared.StyleDTO;

public class CategoryDataProvider extends FilterDataProvider<CategoryDTO> implements CreateStyleHandler, UpdateStyleHandler, DeleteStyleHandler,
		MoveDownStyleHandler, MoveUpStyleHandler, SelectStyleHandler, ClearStyleSelectionHandler, CreateCategoryHandler, UpdateCategoryHandler,
		DeleteCategoryHandler, SelectCategoryHandler, MoveUpCategoryHandler, MoveDownCategoryHandler, ShowChooseCategoryAndStylePopupHandler {

	protected CategoryDTO categoryFilter = null;

	protected Set<String> styleFilter;

	protected Map<String, StyleDTO> styles;

	protected FilterDataProvider<StyleDTO> styleProvider;
	
	public CategoryDataProvider(ArticleTypeDataProvider articleTypeProvider) {
		super(articleTypeProvider);
		styleProvider = new StyleDataProvider(articleTypeProvider);
		styleFilter = new HashSet<String>();
		styles = new HashMap<String, StyleDTO>();
		registerForEvents();
	}

	@Override
	protected String getAttributeContent(ArticleTypeDTO articleType) {
		articleType.getCategoryKey();
		return null;
	}

	public CategoryDTO getCategoryFilter() {
		return categoryFilter;
	}

	public void setCategoryFilter(CategoryDTO categoryFilter) {
		this.categoryFilter = categoryFilter;
	}

	public void updateStyles(List<ArticleTypeDTO> articleTypes, HashMap<String, ArticleAmountDTO> articleAmounts) {
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
				scd.setSelected(styleFilter.contains(scd.getKey()));
			}
			refresh();
		} else {
			styleProvider.getAllItems().clear();
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

	public FilterDataProvider<StyleDTO> getStyleProvider() {
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
				if (categoryFilter.getKey().equals(at.getCategoryKey())) {
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
					if (styleFilter.contains(at.getStyleKey())) {
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
				styles.put(s.getKey(), s);
			}
		}
	}

	@Override
	public void onCreateCategory(CreateCategoryEvent event) {
		final CategoryDTO category = event.getCellData();
		getAllItems().add(category);
		saveCategoryList();
	}

	@Override
	public void onUpdateCategory(UpdateCategoryEvent event) {
		final CategoryDTO item = event.getCellData();
		if (item == null) {
			saveCategoryList();
		} else {
			saveCategory(item);
		}
	}

	private void saveCategory(final CategoryDTO category) {
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

	protected void saveCategoryList() {
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

	@Override
	public void onDeleteCategory(DeleteCategoryEvent event) {
		final CategoryDTO category = event.getCellData();
		if (doesCategoryHaveArticles(category)) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.categoryIsNotEmpty(category.getName())));
			return;
		}
		if (category.equals(getCategoryFilter())) {
			setCategoryFilter(null);
		}
		category.setHidden(!category.getHidden());
		saveCategoryList();
		clearNameAndStyleFilters();
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
		if (getCategoryFilter() != null) {
			getCategoryFilter().setSelected(false);
		}
		CategoryDTO dto = event.getCellData();
		setCategoryFilter(dto);
		if (dto != null) {
			dto.setSelected(true);
		}
		fireRefreshEvent();
		clearNameAndStyleFilters();
	}

	@Override
	public void onMoveDownCategory(MoveDownCategoryEvent event) {
		moveDownCategory(event.getIndex());
	}

	@Override
	public void onMoveUpCategory(MoveUpCategoryEvent event) {
		moveDownCategory(event.getIndex() - 1);
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
		saveCategoryList();
	}

	@Override
	public void onUpdateStyle(UpdateStyleEvent event) {
		saveCategory(getCategoryFilter());
	}

	@Override
	public void onSelectStyle(SelectStyleEvent event) {
		StyleDTO dto = event.getCellData();
		if (dto.isSelected()) {
			getStyleFilter().remove(dto.getKey());
			dto.setSelected(false);
		} else {
			getStyleFilter().add(dto.getKey());
			dto.setSelected(true);
		}
		fireRefreshEvent();
	}

	@Override
	public void onClearStyleSelection(ClearStyleSelectionEvent event) {
		clearStyleSelection();
	}

	@Override
	public void onMoveDownStyle(MoveDownStyleEvent event) {
		moveDownStyle(event.getIndex());
	}

	@Override
	public void onMoveUpStyle(MoveUpStyleEvent event) {
		moveDownStyle(event.getIndex() - 1);
	}

	public void moveDownStyle(int idx) {
		List<StyleDTO> styleList = getCategoryFilter().getStyles();
		if (idx < 0) {
			return;
		}
		if (idx + 1 >= styleList.size()) {
			return;
		}
		StyleDTO item = styleList.remove(idx);
		styleList.add(idx + 1, item);
		saveCategory(getCategoryFilter());
	}

	@Override
	public void onDeleteStyle(DeleteStyleEvent event) {
		final StyleDTO style = event.getCellData();
		CategoryDTO category = getCategoryFilter();
		style.setHidden(style.getHidden());
		saveCategory(category);
	}

	@Override
	public void onCreateStyle(CreateStyleEvent event) {
		final StyleDTO style = event.getCellData();
		if (getCategoryFilter() != null) {
			CategoryDTO category = getCategoryFilter();
			category.getStyles().add(style);
			saveCategory(category);
		} else {
			Xfashion.fireError(errorMessages.noCategorySelected());
		}
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

	@Override
	public void onShowChooseCategoryAndStylePopup(ShowChooseCategoryAndStylePopupEvent event) {
		ChooseCategoryAndStylePopup popup = new ChooseCategoryAndStylePopup(this);
		popup.show();
	}

	private void clearNameAndStyleFilters() {
		clearStyleSelection();
		Xfashion.eventBus.fireEvent(new NameFilterEvent(null));
	}
	
	private void clearStyleSelection() {
		getStyleFilter().clear();
		fireRefreshEvent();
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(CreateCategoryEvent.TYPE, this);
		Xfashion.eventBus.addHandler(UpdateCategoryEvent.TYPE, this);
		Xfashion.eventBus.addHandler(DeleteCategoryEvent.TYPE, this);
		Xfashion.eventBus.addHandler(SelectCategoryEvent.TYPE, this);
		Xfashion.eventBus.addHandler(MoveUpCategoryEvent.TYPE, this);
		Xfashion.eventBus.addHandler(MoveDownCategoryEvent.TYPE, this);
		Xfashion.eventBus.addHandler(CreateStyleEvent.TYPE, this);
		Xfashion.eventBus.addHandler(UpdateStyleEvent.TYPE, this);
		Xfashion.eventBus.addHandler(DeleteStyleEvent.TYPE, this);
		Xfashion.eventBus.addHandler(SelectStyleEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ClearStyleSelectionEvent.TYPE, this);
		Xfashion.eventBus.addHandler(MoveUpStyleEvent.TYPE, this);
		Xfashion.eventBus.addHandler(MoveDownStyleEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowChooseCategoryAndStylePopupEvent.TYPE, this);
	}

	private void storeCategories(List<CategoryDTO> result) {
		setAllItems(result);
		refreshResolver();
		fireRefreshEvent();
		Xfashion.eventBus.fireEvent(new CategoriesLoadedEvent());
	}

}

