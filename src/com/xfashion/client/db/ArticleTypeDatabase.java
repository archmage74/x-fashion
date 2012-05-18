package com.xfashion.client.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.brand.BrandDataProvider;
import com.xfashion.client.cat.CategoryDataProvider;
import com.xfashion.client.color.ColorDataProvider;
import com.xfashion.client.name.NameFilterEvent;
import com.xfashion.client.name.NameFilterHandler;
import com.xfashion.client.size.SizeDataProvider;
import com.xfashion.client.style.UpdateStyleEvent;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public class ArticleTypeDatabase implements ProvidesArticleFilter, NameFilterHandler, RefreshFilterHandler {

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

	public ArticleTypeDatabase() {

	}

	public void init() {
		registerForEvents();

		articleTypeProvider = new ArticleTypeDataProvider();
		categoryProvider = new CategoryDataProvider(articleTypeProvider);
		brandProvider = new BrandDataProvider(articleTypeProvider);
		sizeProvider = new SizeDataProvider(articleTypeProvider);
		colorProvider = new ColorDataProvider(articleTypeProvider);
		nameProvider = new ListDataProvider<String>();
		nameOracle = new MultiWordSuggestOracle();

		readCategories();
		readBrands();
		readSizes();
		readColors();
		readArticleTypes();
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(RefreshFilterEvent.TYPE, this);
		Xfashion.eventBus.addHandler(NameFilterEvent.TYPE, this);
	}

	private void checkAllRead() {
		if (categoryProvider.isLoaded() && brandProvider.isLoaded() && sizeProvider.isLoaded() && colorProvider.isLoaded()
				&& articleTypeProvider.isLoaded()) {
			updateProviders();
			Xfashion.eventBus.fireEvent(new ArticlesDatabaseLoadedEvent());
		}

	}

	private void readCategories() {
		AsyncCallback<List<CategoryDTO>> callback = new AsyncCallback<List<CategoryDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(List<CategoryDTO> result) {
				List<CategoryDTO> list = categoryProvider.getList();
				list.clear();
				list.addAll(result);
				categoryProvider.refreshResolver();
				categoryProvider.setLoaded(true);
				checkAllRead();
			}
		};

		articleTypeService.readCategories(callback);
	}

	private void readBrands() {
		AsyncCallback<List<BrandDTO>> callback = new AsyncCallback<List<BrandDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(List<BrandDTO> result) {
				List<BrandDTO> list = brandProvider.getList();
				list.clear();
				list.addAll(result);
				brandProvider.refreshResolver();
				brandProvider.setLoaded(true);
				checkAllRead();
			}
		};
		articleTypeService.readBrands(callback);
	}

	private void readSizes() {
		AsyncCallback<List<SizeDTO>> callback = new AsyncCallback<List<SizeDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(List<SizeDTO> result) {
				if (result == null) {
					result = new ArrayList<SizeDTO>();
				}
				List<SizeDTO> list = sizeProvider.getList();
				list.clear();
				list.addAll(result);
				sizeProvider.refreshResolver();
				sizeProvider.setLoaded(true);
				checkAllRead();
			}
		};
		articleTypeService.readSizes(callback);
	}

	private void readColors() {
		AsyncCallback<List<ColorDTO>> callback = new AsyncCallback<List<ColorDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(List<ColorDTO> result) {
				List<ColorDTO> list = colorProvider.getList();
				list.clear();
				list.addAll(result);
				colorProvider.refreshResolver();
				colorProvider.setLoaded(true);
				checkAllRead();
			}
		};
		articleTypeService.readColors(callback);
	}

	private void readArticleTypes() {
		AsyncCallback<List<ArticleTypeDTO>> callback = new AsyncCallback<List<ArticleTypeDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(List<ArticleTypeDTO> result) {
				articleTypeProvider.getList().clear();
				articleTypes = new ArrayList<ArticleTypeDTO>(result);
				articleTypeProvider.getList().addAll(result);
				articleTypeProvider.refreshResolver();
				updateAvailableArticleNames();
				articleTypeProvider.setLoaded(true);
				checkAllRead();
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

	public void addBrandDisplay(HasData<BrandDTO> display) {
		brandProvider.addDataDisplay(display);
	}

	public void addSizeDisplay(HasData<SizeDTO> display) {
		sizeProvider.addDataDisplay(display);
	}

	public void addColorDisplay(HasData<ColorDTO> display) {
		colorProvider.addDataDisplay(display);
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

	public void onUpdateStyle(UpdateStyleEvent event) {
		final StyleDTO style = event.getCellData();
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				categoryProvider.refresh();
			}
		};
		articleTypeService.updateStyle(style, callback);
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

	public class StyleCell {
		public String name;
		public boolean available;
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

}
