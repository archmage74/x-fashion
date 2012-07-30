package com.xfashion.client.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.ArticleTypePopup;
import com.xfashion.client.at.EditArticleTypePopup;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.at.bulk.UpdateArticleTypesEvent;
import com.xfashion.client.at.bulk.UpdateArticleTypesHandler;
import com.xfashion.client.at.event.DeleteArticleTypeEvent;
import com.xfashion.client.at.event.DeleteArticleTypeHandler;
import com.xfashion.client.brand.BrandDataProvider;
import com.xfashion.client.cat.CategoryDataProvider;
import com.xfashion.client.color.ColorDataProvider;
import com.xfashion.client.db.event.ArticlesLoadedEvent;
import com.xfashion.client.db.event.FilterRefreshedEvent;
import com.xfashion.client.db.event.RefreshFilterEvent;
import com.xfashion.client.db.event.RefreshFilterHandler;
import com.xfashion.client.db.event.RequestShowArticleTypeDetailsEvent;
import com.xfashion.client.db.event.RequestShowArticleTypeDetailsHandler;
import com.xfashion.client.db.event.SortArticlesEvent;
import com.xfashion.client.db.event.SortArticlesHandler;
import com.xfashion.client.db.sort.IArticleTypeComparator;
import com.xfashion.client.name.NameFilterEvent;
import com.xfashion.client.name.NameFilterHandler;
import com.xfashion.client.size.SizeDataProvider;
import com.xfashion.client.user.UserManagement;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.PriceChangeDTO;
import com.xfashion.shared.UserRole;

public class ArticleTypeDatabase implements ProvidesArticleFilter, NameFilterHandler, RefreshFilterHandler, UpdateArticleTypesHandler,
		RequestShowArticleTypeDetailsHandler, DeleteArticleTypeHandler, SortArticlesHandler {

	private ArticleTypeServiceAsync articleTypeService = (ArticleTypeServiceAsync) GWT.create(ArticleTypeService.class);

	protected ArrayList<ArticleTypeDTO> articleTypes;
	protected List<ArticleTypeDTO> filteredArticleTypes;

	protected CategoryDataProvider categoryProvider;
	protected BrandDataProvider brandProvider;
	protected ColorDataProvider colorProvider;
	protected SizeDataProvider sizeProvider;
	protected ListDataProvider<String> nameProvider;
	protected MultiWordSuggestOracle nameOracle;
	protected ArticleTypeDataProvider articleTypeProvider;
	protected HashMap<String, ArticleAmountDTO> stockArticleAmounts;
	protected IArticleTypeComparator sortStrategy;

	private String nameFilter = null;

	protected ArticleTypePopup articleTypePopup = null;
	
	public ArticleTypeDatabase() {

	}

	public CategoryDataProvider getCategoryProvider() {
		return categoryProvider;
	}

	public BrandDataProvider getBrandProvider() {
		return brandProvider;
	}

	public SizeDataProvider getSizeProvider() {
		return sizeProvider;
	}

	public ColorDataProvider getColorProvider() {
		return colorProvider;
	}

	public ArticleTypeDataProvider getArticleTypeProvider() {
		return articleTypeProvider;
	}

	public MultiWordSuggestOracle getNameOracle() {
		return nameOracle;
	}

	public void setNameOracle(MultiWordSuggestOracle nameOracle) {
		this.nameOracle = nameOracle;
	}

	public ListDataProvider<String> getNameProvider() {
		return nameProvider;
	}

	public void setNameProvider(ListDataProvider<String> nameProvider) {
		this.nameProvider = nameProvider;
	}

	public HashMap<String, ArticleAmountDTO> getArticleAmounts() {
		return stockArticleAmounts;
	}

	public void setArticleAmounts(HashMap<String, ArticleAmountDTO> articleAmounts) {
		this.stockArticleAmounts = articleAmounts;
	}

	public IArticleTypeComparator getSortStrategy() {
		return sortStrategy;
	}

	public void setSortStrategy(IArticleTypeComparator sortStrategy) {
		this.sortStrategy = sortStrategy;
	}

	public void init() {
		articleTypes = new ArrayList<ArticleTypeDTO>();

		articleTypeProvider = new ArticleTypeDataProvider();
		categoryProvider = new CategoryDataProvider(articleTypeProvider);
		brandProvider = new BrandDataProvider(articleTypeProvider);
		sizeProvider = new SizeDataProvider(articleTypeProvider);
		colorProvider = new ColorDataProvider(articleTypeProvider);
		nameProvider = new ListDataProvider<String>();
		nameOracle = new MultiWordSuggestOracle();

		categoryProvider.readCategories();
		brandProvider.readBrands();
		sizeProvider.readSizes();
		colorProvider.readColors();
		readArticleTypes();

		registerForEvents();
	}

	private void readArticleTypes() {
		AsyncCallback<Set<ArticleTypeDTO>> callback = new AsyncCallback<Set<ArticleTypeDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(Set<ArticleTypeDTO> result) {
				storeArticles(result);
			}
		};
		articleTypeService.readArticleTypes(callback);
	}

	private void storeArticles(Set<ArticleTypeDTO> result) {
		articleTypeProvider.getList().clear();
		articleTypes = new ArrayList<ArticleTypeDTO>(result);
		articleTypeProvider.getList().addAll(result);
		articleTypeProvider.refreshResolver();
		Xfashion.eventBus.fireEvent(new ArticlesLoadedEvent());
		Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
	}
	
	public Collection<String> getArticleNames(List<ArticleTypeDTO> articleTypes) {
		HashSet<String> names = new HashSet<String>();
		for (ArticleTypeDTO at : articleTypes) {
			if (stockArticleAmounts == null) {
				names.add(at.getName());
			} else {
				ArticleAmountDTO am = stockArticleAmounts.get(at.getKey());
				if (am != null && am.getAmount() > 0) {
					names.add(at.getName());
				}
			}
		}
		return names;
	}

	@Override
	public void onRefreshFilter(RefreshFilterEvent event) {
		applyFilters();
		updateProviders();
		if (sortStrategy != null) {
			Collections.sort(articleTypeProvider.getList(), sortStrategy);
		}
		Xfashion.eventBus.fireEvent(new FilterRefreshedEvent());
	}

	public void updateAvailableArticleNames() {
		List<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>(articleTypes);
		result = categoryProvider.applyFilter(result);
		result = brandProvider.applyFilter(result);
		result = sizeProvider.applyFilter(result);
		result = colorProvider.applyFilter(result);

		List<String> nameList = new ArrayList<String>(getArticleNames(result));
		Collections.sort(nameList);
		nameProvider.setList(nameList);
		nameOracle.clear();
		nameOracle.addAll(nameList);
	}

	public void applyFilters() {
		List<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>(articleTypes);

		result = categoryProvider.applyFilter(result);
		result = brandProvider.applyFilter(result);
		result = sizeProvider.applyFilter(result);
		result = colorProvider.applyFilter(result);
		result = applyNameFilter(nameFilter, result);

		filteredArticleTypes = result;
		articleTypeProvider.setList(filteredArticleTypes);
	}

	private List<ArticleTypeDTO> applyNameFilter(String name, List<ArticleTypeDTO> articles) {
		ArrayList<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>(articles);
		ArrayList<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>();

		if (name != null) {
			for (ArticleTypeDTO at : result) {
				if (name.equals(at.getName())) {
					temp.add(at);
				}
			}
			result.retainAll(temp);
		}
		return result;
	}

	@Override
	public void onNameFilter(NameFilterEvent event) {
		nameFilter = event.getName();
		Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
	}

	public void setCategoryFilter(CategoryDTO category) {
		categoryProvider.setCategoryFilter(category);
		applyFilters();
		updateProviders();
	}

	public void updateProviders() {
		categoryProvider.refresh();
		updateBrandProvider();
		updateStyleProvider();
		updateSizeProvider();
		updateColorProvider();
		updateAvailableArticleNames();
	}

	public void updateStyleProvider() {
		categoryProvider.refreshResolver();
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypes);
		temp = categoryProvider.applyCategoryFilter(temp);
		temp = sizeProvider.applyFilter(temp);
		temp = colorProvider.applyFilter(temp);
		temp = applyNameFilter(nameFilter, temp);
		categoryProvider.updateStyles(temp, stockArticleAmounts);
	}

	public void updateBrandProvider() {
		brandProvider.refreshResolver();
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypes);
		temp = categoryProvider.applyFilter(temp);
		temp = sizeProvider.applyFilter(temp);
		temp = colorProvider.applyFilter(temp);
		temp = applyNameFilter(nameFilter, temp);
		brandProvider.update(temp, stockArticleAmounts);
	}

	public void updateSizeProvider() {
		sizeProvider.refreshResolver();
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypes);
		temp = categoryProvider.applyFilter(temp);
		temp = brandProvider.applyFilter(temp);
		temp = colorProvider.applyFilter(temp);
		temp = applyNameFilter(nameFilter, temp);
		sizeProvider.update(temp, stockArticleAmounts);
	}

	public void updateColorProvider() {
		colorProvider.refreshResolver();
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypes);
		temp = categoryProvider.applyFilter(temp);
		temp = brandProvider.applyFilter(temp);
		temp = sizeProvider.applyFilter(temp);
		temp = applyNameFilter(nameFilter, temp);
		colorProvider.update(temp, stockArticleAmounts);
	}

	public void addArticleTypeDisplay(HasData<ArticleTypeDTO> display) {
		articleTypeProvider.addDataDisplay(display);
	}

	public void createArticleType(final ArticleTypeDTO articleType) {
		AsyncCallback<ArticleTypeDTO> callback = new AsyncCallback<ArticleTypeDTO>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(ArticleTypeDTO result) {
				articleTypes.add(result);
				articleTypeProvider.getList().add(result);
				articleTypeProvider.refreshResolver();
				updateAvailableArticleNames();
				applyFilters();
				sortArticles();
			}
		};
		articleTypeService.createArticleType(articleType, callback);
	}

	@Override
	public void onUpdateArticleTypes(UpdateArticleTypesEvent event) {
		for (ArticleTypeDTO at : event.getArticleTypes()) {
			updateArticleType(at);
		}
		createPriceChanges(event.getPriceChanges());
	}

	public void createPriceChanges(Collection<PriceChangeDTO> priceChanges) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				
			}
		};
		articleTypeService.createPriceChanges(priceChanges, callback);
	}
		
	@Override
	public void onRequestShowArticleTypeDetails(RequestShowArticleTypeDetailsEvent event) {
		if (UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
			if (articleTypePopup == null) {
				articleTypePopup = new EditArticleTypePopup(this);
			}
			articleTypePopup.showPopup(event.getArticleType());
		}
	}

	public void updateArticleType(final ArticleTypeDTO articleType) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				articleTypeProvider.refresh();
				applyFilters();
				updateProviders();
			}
		};
		articleTypeService.updateArticleType(articleType, callback);
	}

	@Override
	public void onDeleteArticleType(DeleteArticleTypeEvent event) {
		deleteArticleType(event.getArticleType());
	}
	
	public void deleteArticleType(final ArticleTypeDTO articleType) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				articleTypes.remove(articleType);
				applyFilters();
				updateProviders();
			}
		};
		articleTypeService.deleteArticleType(articleType, callback);
	}
	
	@Override
	public void onSortArticles(SortArticlesEvent event) {
		if (event.getArticleTypeComparator() != null) {
			this.sortStrategy = event.getArticleTypeComparator();
		}
		sortArticles();
	}
	
	private void sortArticles() {
		Collections.sort(articleTypeProvider.getList(), sortStrategy);
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(RefreshFilterEvent.TYPE, this);
		Xfashion.eventBus.addHandler(NameFilterEvent.TYPE, this);
		Xfashion.eventBus.addHandler(UpdateArticleTypesEvent.TYPE, this);
		Xfashion.eventBus.addHandler(DeleteArticleTypeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestShowArticleTypeDetailsEvent.TYPE, this);
		Xfashion.eventBus.addHandler(SortArticlesEvent.TYPE, this);
	}

}
