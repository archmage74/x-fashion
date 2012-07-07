package com.xfashion.client.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import com.xfashion.client.at.ArticleTypeDetailPopup;
import com.xfashion.client.at.DeleteArticleTypeEvent;
import com.xfashion.client.at.DeleteArticleTypeHandler;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.at.UpdateArticleTypeEvent;
import com.xfashion.client.at.UpdateArticleTypeHandler;
import com.xfashion.client.at.bulk.UpdateArticleTypesEvent;
import com.xfashion.client.at.bulk.UpdateArticleTypesHandler;
import com.xfashion.client.brand.BrandDataProvider;
import com.xfashion.client.cat.CategoryDataProvider;
import com.xfashion.client.color.ColorDataProvider;
import com.xfashion.client.db.event.FilterRefreshedEvent;
import com.xfashion.client.db.event.RefreshFilterEvent;
import com.xfashion.client.db.event.RefreshFilterHandler;
import com.xfashion.client.name.NameFilterEvent;
import com.xfashion.client.name.NameFilterHandler;
import com.xfashion.client.size.SizeDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.CategoryDTO;

public class ArticleTypeDatabase implements ProvidesArticleFilter, NameFilterHandler, RefreshFilterHandler, UpdateArticleTypesHandler,
		RequestShowArticleTypeDetailsHandler, UpdateArticleTypeHandler, DeleteArticleTypeHandler {

	private ArticleTypeServiceAsync articleTypeService = (ArticleTypeServiceAsync) GWT.create(ArticleTypeService.class);

	public String[] categories = null;

	private ArrayList<ArticleTypeDTO> articleTypes;
	private List<ArticleTypeDTO> filteredArticleTypes;

	private CategoryDataProvider categoryProvider;
	private BrandDataProvider brandProvider;
	private ColorDataProvider colorProvider;
	private SizeDataProvider sizeProvider;
	private ListDataProvider<String> nameProvider;
	private MultiWordSuggestOracle nameOracle;
	private ArticleTypeDataProvider articleTypeProvider;

	private String nameFilter = null;

	ArticleTypeDetailPopup articleTypeDetailPopup = null;
	
	public ArticleTypeDatabase() {

	}

	public void init() {
		registerForEvents();
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
	}

	private void readArticleTypes() {
		AsyncCallback<Set<ArticleTypeDTO>> callback = new AsyncCallback<Set<ArticleTypeDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(Set<ArticleTypeDTO> result) {
				articleTypeProvider.getList().clear();
				articleTypes = new ArrayList<ArticleTypeDTO>(result);
				articleTypeProvider.getList().addAll(result);
				articleTypeProvider.refreshResolver();
				updateAvailableArticleNames();
			}
		};
		articleTypeService.readArticleTypes(callback);
	}

	public Collection<String> getArticleNames(List<ArticleTypeDTO> articleTypes) {
		HashSet<String> names = new HashSet<String>();
		for (ArticleTypeDTO at : articleTypes) {
			names.add(at.getName());
		}
		return names;
	}

	@Override
	public void onRefreshFilter(RefreshFilterEvent event) {
		applyFilters();
		updateProviders();
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
		applyFilters();
		updateProviders();
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
		categoryProvider.updateStyles(temp);
	}

	public void updateBrandProvider() {
		brandProvider.refreshResolver();
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypes);
		temp = categoryProvider.applyFilter(temp);
		temp = sizeProvider.applyFilter(temp);
		temp = colorProvider.applyFilter(temp);
		temp = applyNameFilter(nameFilter, temp);
		brandProvider.update(temp);
	}

	public void updateSizeProvider() {
		sizeProvider.refreshResolver();
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypes);
		temp = categoryProvider.applyFilter(temp);
		temp = brandProvider.applyFilter(temp);
		temp = colorProvider.applyFilter(temp);
		temp = applyNameFilter(nameFilter, temp);
		sizeProvider.update(temp);
	}

	public void updateColorProvider() {
		colorProvider.refreshResolver();
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypes);
		temp = categoryProvider.applyFilter(temp);
		temp = brandProvider.applyFilter(temp);
		temp = sizeProvider.applyFilter(temp);
		temp = applyNameFilter(nameFilter, temp);
		colorProvider.update(temp);
	}

	public void addArticleTypeDisplay(HasData<ArticleTypeDTO> display) {
		articleTypeProvider.addDataDisplay(display);
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
			}
		};
		articleTypeService.createArticleType(articleType, callback);
	}

	@Override
	public void onUpdateArticleTypes(UpdateArticleTypesEvent event) {
		for (ArticleTypeDTO at : event.getArticleTypes()) {
			updateArticleType(at);
		}
	}

	@Override
	public void onUpdateArticleType(UpdateArticleTypeEvent event) {
		updateArticleType(event.getArticleType());
	}

	@Override
	public void onRequestShowArticleTypeDetails(RequestShowArticleTypeDetailsEvent event) {
		if (articleTypeDetailPopup == null) {
			articleTypeDetailPopup = new ArticleTypeDetailPopup(this);
		}
		articleTypeDetailPopup.showPopup(event.getArticleType());
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

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(RefreshFilterEvent.TYPE, this);
		Xfashion.eventBus.addHandler(NameFilterEvent.TYPE, this);
		Xfashion.eventBus.addHandler(UpdateArticleTypesEvent.TYPE, this);
		Xfashion.eventBus.addHandler(UpdateArticleTypeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(DeleteArticleTypeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(RequestShowArticleTypeDetailsEvent.TYPE, this);
	}

}
