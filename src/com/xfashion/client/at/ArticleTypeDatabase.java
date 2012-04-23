package com.xfashion.client.at;

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
import com.xfashion.client.ApplicationErrorListener;
import com.xfashion.client.ApplicationLoadListener;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.brand.BrandDataProvider;
import com.xfashion.client.cat.CategoryCellData;
import com.xfashion.client.cat.CategoryDataProvider;
import com.xfashion.client.color.ColorCellData;
import com.xfashion.client.color.ColorDataProvider;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.size.SizeCellData;
import com.xfashion.client.size.SizeDataProvider;
import com.xfashion.client.style.StyleDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.FilterCellData;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public class ArticleTypeDatabase {

	private ArticleTypeServiceAsync articleTypeService = (ArticleTypeServiceAsync) GWT.create(ArticleTypeService.class);

	public String[] categories = null;

	private ArrayList<ArticleTypeDTO> articleTypes;
	private List<ArticleTypeDTO> filteredArticleTypes;

	private CategoryDataProvider categoryProvider;
	private StyleDataProvider styleProvider;
	private BrandDataProvider brandProvider;
	private ColorDataProvider colorProvider;
	private SizeDataProvider sizeProvider;
	private MultiWordSuggestOracle nameOracle;
	private ArticleTypeDataProvider articleTypeProvider;

	private CategoryDTO categoryFilter = null;
	private String nameFilter = null;
	
	private ApplicationLoadListener applicationLoadListener;
	private ApplicationErrorListener applicationErrorListener;
	
	private ErrorMessages errorMessages;

	public ArticleTypeDatabase() {
		init();
	}

	public void init() {
		errorMessages = GWT.create(ErrorMessages.class);
		
		categoryProvider = new CategoryDataProvider();
		styleProvider = new StyleDataProvider();
		brandProvider = new BrandDataProvider();
		sizeProvider = new SizeDataProvider();
		colorProvider = new ColorDataProvider();
		articleTypeProvider = new ArticleTypeDataProvider();
		nameOracle = new MultiWordSuggestOracle();

		readCategories();
		readStyles();
		readBrands();
		readSizes();
		readColors();
		readArticleTypes();
	}

	public void createCategories() {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(Void result) {
			}
		};
		articleTypeService.createCategories(callback);
	}

	private void checkAllRead() {
		if (categoryProvider.isLoaded() &&
				styleProvider.isLoaded() &&
				brandProvider.isLoaded() &&
				sizeProvider.isLoaded() &&
				colorProvider.isLoaded() &&
				articleTypeProvider.isLoaded()) {
			updateProviders();
		}
		fireApplicationLoaded();
			
	}
	
	private void fireApplicationLoaded() {
		if (applicationLoadListener != null) {
			applicationLoadListener.applicationLoaded();
		}
	}
	
	private void readCategories() {
		AsyncCallback<List<CategoryDTO>> callback = new AsyncCallback<List<CategoryDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(List<CategoryDTO> result) {
				Collections.sort(result);
				List<CategoryCellData> list = categoryProvider.getList();
				list.clear();
				for (CategoryDTO dto : result) {
					CategoryCellData ccd = new CategoryCellData(dto);
					list.add(ccd);
				}
				categoryProvider.setLoaded(true);
				checkAllRead();
			}
		};

		articleTypeService.readCategories(callback);
	}

	private void readStyles() {
		AsyncCallback<List<StyleDTO>> callback = new AsyncCallback<List<StyleDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(List<StyleDTO> result) {
				Collections.sort(result);
				List<StyleDTO> list = styleProvider.getList();
				list.clear();
				list.addAll(result);
				styleProvider.setLoaded(true);
				checkAllRead();
			}
		};
		articleTypeService.readStyles(callback);
	}
	
	private void readBrands() {
		AsyncCallback<List<BrandDTO>> callback = new AsyncCallback<List<BrandDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(List<BrandDTO> result) {
				Collections.sort(result);
				List<BrandDTO> list = brandProvider.getList();
				list.clear();
				list.addAll(result);
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
			}
			@Override
			public void onSuccess(List<SizeDTO> result) {
				List<SizeCellData> list = sizeProvider.getList();
				for (SizeDTO dto : result) {
					SizeCellData scd = new SizeCellData(dto.getName());
					list.add(scd);
				}
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
			}
			@Override
			public void onSuccess(List<ColorDTO> result) {
				List<ColorCellData> list = colorProvider.getList();
				for (ColorDTO dto : result) {
					ColorCellData ccd = new ColorCellData(dto.getName());
					list.add(ccd);
				}
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
			}
			@Override
			public void onSuccess(List<ArticleTypeDTO> result) {
				articleTypeProvider.getList().clear();
				articleTypes = new ArrayList<ArticleTypeDTO>(result);
				articleTypeProvider.getList().addAll(result);
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
	
	public void updateAvailableArticleNames() {
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypes);
		temp = applyCategoryFilter(categoryFilter, temp);
		temp = applyFilter(brandProvider, temp);
		temp = applyFilter(styleProvider, temp);
		temp = applyFilter(sizeProvider, temp);
		temp = applyFilter(colorProvider, temp);
		
		Collection<String> names = getArticleNames(temp);
		nameOracle.clear();
		nameOracle.addAll(names);
	}
	
	private List<ArticleTypeDTO> applyFilter(FilterDataProvider<? extends FilterCellData> provider, List<ArticleTypeDTO> articleTypes) {
		if (articleTypes == null) {
			articleTypes = new ArrayList<ArticleTypeDTO>();
		}
		ArrayList<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>();
		Set<String> filter = provider.getFilter();
		
		if (filter != null && filter.size() > 0) {
			temp.clear();
			for (ArticleTypeDTO at : articleTypes) {
				if (filter.contains(provider.getAttributeContent(at))) {
					temp.add(at);
				}
			}
			articleTypes.retainAll(temp);
		}
		
		return articleTypes;
	}
	
	public void applyFilters() {
		List<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>(articleTypes);

		result = applyCategoryFilter(categoryFilter, result);
		result = applyFilter(styleProvider, result);
		result = applyFilter(brandProvider, result);
		result = applyFilter(sizeProvider, result);
		result = applyFilter(colorProvider, result);
		result = applyNameFilter(nameFilter, result);

		filteredArticleTypes = result;
		articleTypeProvider.setList(filteredArticleTypes);
	}
	
	private List<ArticleTypeDTO> applyCategoryFilter(CategoryDTO categoryFilter, List<ArticleTypeDTO> articles) {
		ArrayList<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>(articleTypes);
		ArrayList<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>();

		if (categoryFilter != null) {
			for (ArticleTypeDTO at : result) {
				if (categoryFilter.getName().equals(at.getCategory())) {
					temp.add(at);
				}
			}
			result.retainAll(temp);
		}
		return result;
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

	public CategoryDTO getCategoryFilter() {
		return categoryFilter;
	}
	
	public void setNameFilter(String name) {
		nameFilter = name;
		applyFilters();
		updateProviders();
	}

	public void setCategoryFilter(CategoryDTO category) {
		categoryFilter = category;
		applyFilters();
		updateProviders();
	}

	public void updateFilterProvider(FilterDataProvider<? extends FilterCellData> provider, List<ArticleTypeDTO> articleTypes) {
		HashMap<String, Integer> articleAmountPerAttribute = new HashMap<String, Integer>();
		for (ArticleTypeDTO at : articleTypes) {
			String attributeContent = provider.getAttributeContent(at);
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
		List<? extends FilterCellData> cellDataList = provider.getList();
		for (FilterCellData scd : cellDataList) {
			Integer availableArticles = articleAmountPerAttribute.get(scd.getName());
			if (availableArticles == null) {
				availableArticles = 0;
			}
			scd.setArticleAmount(availableArticles);
			scd.setSelected(provider.getFilter().contains(scd.getName()));
		}
		provider.refresh();
	}
	
	public void updateProviders() {
		updateStyleProvider();
		updateBrandProvider();
		updateSizeProvider();
		updateColorProvider();
		updateAvailableArticleNames();
	}
	
	public void updateStyleProvider() {
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypes);
		temp = applyCategoryFilter(categoryFilter, temp);
		temp = applyFilter(brandProvider, temp);
		temp = applyFilter(sizeProvider, temp);
		temp = applyFilter(colorProvider, temp);
		temp = applyNameFilter(nameFilter, temp);
		updateFilterProvider(styleProvider, temp);
	}
	
	public void updateBrandProvider() {
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypes);
		temp = applyCategoryFilter(categoryFilter, temp);
		temp = applyFilter(styleProvider, temp);
		temp = applyFilter(sizeProvider, temp);
		temp = applyFilter(colorProvider, temp);
		temp = applyNameFilter(nameFilter, temp);
		updateFilterProvider(brandProvider, temp);
	}

	public void updateSizeProvider() {
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypes);
		temp = applyCategoryFilter(categoryFilter, temp);
		temp = applyFilter(styleProvider, temp);
		temp = applyFilter(brandProvider, temp);
		temp = applyFilter(colorProvider, temp);
		temp = applyNameFilter(nameFilter, temp);
		updateFilterProvider(sizeProvider, temp);
	}
	
	public void updateColorProvider() {
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypes);
		temp = applyCategoryFilter(categoryFilter, temp);
		temp = applyFilter(styleProvider, temp);
		temp = applyFilter(brandProvider, temp);
		temp = applyFilter(sizeProvider, temp);
		temp = applyNameFilter(nameFilter, temp);
		updateFilterProvider(colorProvider, temp);
	}
	
	public Set<String> getStyleFilter() {
		return styleProvider.getFilter();
	}

	public Set<String> getBrandFilter() {
		return brandProvider.getFilter();
	}

	public Set<String> getColorFilter() {
		return colorProvider.getFilter();
	}

	public Set<String> getSizeFilter() {
		return sizeProvider.getFilter();
	}

	public void addBrandDisplay(HasData<BrandDTO> display) {
		brandProvider.addDataDisplay(display);
	}

	public void addSizeDisplay(HasData<SizeCellData> display) {
		sizeProvider.addDataDisplay(display);
	}

	public void addColorDisplay(HasData<ColorCellData> display) {
		colorProvider.addDataDisplay(display);
	}

	public void addArticleTypeDisplay(HasData<ArticleTypeDTO> display) {
		articleTypeProvider.addDataDisplay(display);
	}

	public CategoryDataProvider getCategoryProvider() {
		return categoryProvider;
	}

	public StyleDataProvider getStyleProvider() {
		return styleProvider;
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

	public ListDataProvider<ArticleTypeDTO> getArticleTypeProvider() {
		return articleTypeProvider;
	}

	public void createCategory(final CategoryDTO category) {
		AsyncCallback<CategoryDTO> callback = new AsyncCallback<CategoryDTO>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(CategoryDTO result) {
				categoryProvider.getList().add(new CategoryCellData(result));
			}
		};
		articleTypeService.createCategory(category, callback);
	}
	
	public void updateCategory(final CategoryDTO category) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Void result) { 
				categoryProvider.refresh();
			}
		};
		articleTypeService.updateCategory(category, callback);
	}
	
	public void deleteCategory(final CategoryCellData category) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				String msg = caught.getMessage();
				applicationErrorListener.error(errorMessages.categoryDeleteFailed(msg));
			}
			@Override
			public void onSuccess(Void result) {
				categoryProvider.getList().remove(category);
			}
		};
		articleTypeService.deleteCategory(category.getCategoryDTO(), callback);
	}

	public boolean doesCategoryHaveArticles(CategoryCellData category) {
		for (ArticleTypeDTO at : articleTypes) {
			if (at.getCategory().equals(category.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public void createStyle(final StyleDTO dto) {
		AsyncCallback<StyleDTO> callback = new AsyncCallback<StyleDTO>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(StyleDTO result) {
				styleProvider.getList().add(result);
				updateStyleProvider();
			}
		};
		articleTypeService.createStyle(dto, callback);
	}

	public void updateStyle(StyleDTO dto) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Void result) { 
				brandProvider.refresh();
			}
		};
		articleTypeService.updateStyle(dto, callback);
	}

	public void deleteStyle(final StyleDTO dto) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				String msg = caught.getMessage();
				applicationErrorListener.error(errorMessages.styleDeleteFailed(msg));
			}
			@Override
			public void onSuccess(Void result) {
				styleProvider.getList().remove(dto);
			}
		};
		articleTypeService.deleteStyle(dto, callback);
	}

	public void createBrand(final BrandDTO brand) {
		AsyncCallback<BrandDTO> callback = new AsyncCallback<BrandDTO>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(BrandDTO result) {
				brandProvider.getList().add(result);
				updateBrandProvider();
			}
		};
		articleTypeService.createBrand(brand, callback);
	}
	
	public void updateBrand(BrandDTO brand) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Void result) { 
				brandProvider.refresh();
			}
		};
		articleTypeService.updateBrand(brand, callback);
	}

	public void deleteBrand(final BrandDTO brand) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				String msg = caught.getMessage();
				applicationErrorListener.error(errorMessages.brandDeleteFailed(msg));
			}
			@Override
			public void onSuccess(Void result) {
				brandProvider.getList().remove(brand);
			}
		};
		articleTypeService.deleteBrand(brand, callback);
	}

	public void addSize(final String size) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Void result) { 
				SizeCellData scd = new SizeCellData(size);
				sizeProvider.getList().add(scd);
				updateSizeProvider();
			}
		};
		SizeDTO dto = new SizeDTO();
		dto.setName(size);
		articleTypeService.addSize(dto, callback);
	}

	public void addColor(final String color) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Void result) { 
				ColorCellData ccd = new ColorCellData(color);
				colorProvider.getList().add(ccd);
				updateColorProvider();
			}
		};
		ColorDTO dto = new ColorDTO();
		dto.setName(color);
		articleTypeService.addColor(dto, callback);
	}

	public void addArticleType(final ArticleTypeDTO articleType) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Void result) {
				articleTypes.add(articleType);
				updateProviders();
				applyFilters();
			}
		};
		articleTypeService.addArticleType(articleType, callback);
	}
	
	public class StyleCell {
		public String name;
		public boolean available;
	}

	public ApplicationLoadListener getApplicationLoadListener() {
		return applicationLoadListener;
	}

	public void setApplicationLoadListener(ApplicationLoadListener applicationLoadListener) {
		this.applicationLoadListener = applicationLoadListener;
	}

	public ApplicationErrorListener getApplicationErrorListener() {
		return applicationErrorListener;
	}

	public void setApplicationErrorListener(ApplicationErrorListener applicationErrorListener) {
		this.applicationErrorListener = applicationErrorListener;
	}

	public MultiWordSuggestOracle getNameOracle() {
		return nameOracle;
	}

	public void setNameOracle(MultiWordSuggestOracle nameOracle) {
		this.nameOracle = nameOracle;
	}

}
