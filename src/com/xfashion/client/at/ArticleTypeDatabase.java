package com.xfashion.client.at;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.ApplicationErrorListener;
import com.xfashion.client.ApplicationLoadListener;
import com.xfashion.client.FilterCellData;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.brand.BrandCellData;
import com.xfashion.client.brand.BrandDataProvider;
import com.xfashion.client.cat.CategoryDataProvider;
import com.xfashion.client.color.ColorCellData;
import com.xfashion.client.color.ColorDataProvider;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.size.SizeCellData;
import com.xfashion.client.size.SizeDataProvider;
import com.xfashion.client.style.StyleCellData;
import com.xfashion.client.style.StyleDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
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
		
		createCategoryProvider();
		createStyleProvider();
		createBrandProvider();
		createColorProvider();
		createSizeProvider();
		createArticleTypeProvider();

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
				List<CategoryDTO> list = categoryProvider.getList();
				list.addAll(result);
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
				List<StyleCellData> list = styleProvider.getList();
				for (StyleDTO dto : result) {
					StyleCellData scd = new StyleCellData(dto.getName(), true);
					list.add(scd);
					styleProvider.setLoaded(true);
					checkAllRead();
				}
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
				List<BrandCellData> list = brandProvider.getList();
				for (BrandDTO dto : result) {
					BrandCellData bcd = new BrandCellData(dto.getName(), true);
					list.add(bcd);
					brandProvider.setLoaded(true);
					checkAllRead();
				}
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
					SizeCellData scd = new SizeCellData(dto.getName(), true);
					list.add(scd);
					sizeProvider.setLoaded(true);
					checkAllRead();
				}
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
					ColorCellData ccd = new ColorCellData(dto.getName(), true);
					list.add(ccd);
					colorProvider.setLoaded(true);
					checkAllRead();
				}
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
				articleTypeProvider.setLoaded(true);
				checkAllRead();
			}
		};
		articleTypeService.readArticleTypes(callback);
	}
	
	private void createCategoryProvider() {
		categoryProvider = new CategoryDataProvider();
	}
	
	private void createStyleProvider() {
		styleProvider = new StyleDataProvider();
	}
	
	private void createBrandProvider() {
		brandProvider = new BrandDataProvider();
	}
	
	private void createSizeProvider() {
		sizeProvider = new SizeDataProvider();
	}
	
	private void createColorProvider() {
		colorProvider = new ColorDataProvider();
	}
	
	private void createArticleTypeProvider() {
		articleTypeProvider = new ArticleTypeDataProvider();
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
			scd.setAvailable(availableArticles != null);
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

	public void addBrandDisplay(HasData<BrandCellData> display) {
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

	public ListDataProvider<CategoryDTO> getCategoryProvider() {
		return categoryProvider;
	}

	public ListDataProvider<StyleCellData> getStyleProvider() {
		return styleProvider;
	}

	public ListDataProvider<BrandCellData> getBrandProvider() {
		return brandProvider;
	}

	public ListDataProvider<SizeCellData> getSizeProvider() {
		return sizeProvider;
	}

	public ListDataProvider<ColorCellData> getColorProvider() {
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
				categoryProvider.getList().add(result);
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
	
	public void deleteCategory(final CategoryDTO category) {
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
		articleTypeService.deleteCategory(category, callback);
	}

	public boolean doesCategoryHaveArticles(CategoryDTO category) {
		for (ArticleTypeDTO at : articleTypes) {
			if (at.getCategory().equals(category.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public void addStyle(final String style) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Void result) {
				StyleCellData scd = new StyleCellData(style, true);
				styleProvider.getList().add(scd);
				updateStyleProvider();
			}
		};
		StyleDTO dto = new StyleDTO();
		dto.setName(style);
		articleTypeService.addStyle(dto, callback);
	}

	public void addBrand(final String brand) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Void result) { 
				BrandCellData bcd = new BrandCellData(brand, true);
				brandProvider.getList().add(bcd);
				updateBrandProvider();
			}
		};
		BrandDTO dto = new BrandDTO();
		dto.setName(brand);
		articleTypeService.addBrand(dto, callback);
	}

	public void addSize(final String size) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) { }
			@Override
			public void onSuccess(Void result) { 
				SizeCellData scd = new SizeCellData(size, true);
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
				ColorCellData ccd = new ColorCellData(color, true);
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

}
