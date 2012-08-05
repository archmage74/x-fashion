package com.xfashion.client.at;

import java.util.ArrayList;
import java.util.List;

import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.xfashion.client.SimpleFilterDataProvider;
import com.xfashion.client.at.brand.BrandDataProvider;
import com.xfashion.client.at.category.CategoryDataProvider;
import com.xfashion.client.at.color.ColorDataProvider;
import com.xfashion.client.at.name.NameDataProvider;
import com.xfashion.client.at.size.SizeDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public class ArticleFilterProvider implements IProvideArticleFilter {

	protected CategoryDataProvider categoryProvider;
	protected BrandDataProvider brandProvider;
	protected ColorDataProvider colorProvider;
	protected SizeDataProvider sizeProvider;
	protected NameDataProvider nameProvider;
	protected ArticleTypeDataProvider articleTypeProvider;

	protected EventBus filterEventBus;
	
	public ArticleFilterProvider(ArticleTypeDataProvider articleTypeProvider) {
		this.articleTypeProvider = articleTypeProvider;
		this.filterEventBus = new SimpleEventBus();
		
		this.categoryProvider = new CategoryDataProvider(articleTypeProvider, filterEventBus);
		this.brandProvider = new BrandDataProvider(articleTypeProvider, filterEventBus);
		this.colorProvider = new ColorDataProvider(articleTypeProvider, filterEventBus);
		this.sizeProvider = new SizeDataProvider(articleTypeProvider, filterEventBus);
		this.nameProvider = new NameDataProvider(articleTypeProvider, this, filterEventBus);
	}
	
	public EventBus getFilterEventBus() {
		return filterEventBus;
	}

	@Override
	public CategoryDataProvider getCategoryProvider() {
		return categoryProvider;
	}

	public void setCategoryProvider(CategoryDataProvider categoryProvider) {
		this.categoryProvider = categoryProvider;
	}

	@Override
	public BrandDataProvider getBrandProvider() {
		return brandProvider;
	}

	public void setBrandProvider(BrandDataProvider brandProvider) {
		this.brandProvider = brandProvider;
	}

	@Override
	public SizeDataProvider getSizeProvider() {
		return sizeProvider;
	}

	public void setSizeProvider(SizeDataProvider sizeProvider) {
		this.sizeProvider = sizeProvider;
	}

	@Override
	public ColorDataProvider getColorProvider() {
		return colorProvider;
	}

	public void setColorProvider(ColorDataProvider colorProvider) {
		this.colorProvider = colorProvider;
	}

	@Override
	public NameDataProvider getNameProvider() {
		return nameProvider;
	}
	
	public void setNameProvider(NameDataProvider nameProvider) {
		this.nameProvider = nameProvider;
	}

	public void init() {
		articleTypeProvider.readArticleTypes();
		categoryProvider.readCategories();
		brandProvider.readBrands();
		colorProvider.readColors();
		sizeProvider.readSizes();
	}
	
	@Override
	public void applyFilters(List<ArticleTypeDTO> articles) {
		categoryProvider.applyFilter(articles);
		brandProvider.applyFilter(articles);
		sizeProvider.applyFilter(articles);
		colorProvider.applyFilter(articles);
		nameProvider.applyFilter(articles);
	}

	public ArticleTypeDataProvider getArticleTypeProvider() {
		return articleTypeProvider;
	}

	public void updateProviders() {
		categoryProvider.refresh();
		updateStyleProvider();
		updateProvider(brandProvider);
		updateProvider(colorProvider);
		updateProvider(sizeProvider);
		nameProvider.updateAvailableArticleNames();
	}

	public void updateStyleProvider() {
		categoryProvider.refreshResolver();
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypeProvider.getAllArticleTypes());
		temp = categoryProvider.applyCategoryFilter(temp);
		temp = sizeProvider.applyFilter(temp);
		temp = colorProvider.applyFilter(temp);
		temp = nameProvider.applyFilter(temp);
		categoryProvider.updateStyles(temp);
	}

	public void updateProvider(SimpleFilterDataProvider<?> providerToUpdate) {
		providerToUpdate.refreshResolver();
		List<ArticleTypeDTO> temp = new ArrayList<ArticleTypeDTO>(articleTypeProvider.getAllArticleTypes());
		temp = categoryProvider.applyFilter(temp);
		if (brandProvider != providerToUpdate) {
			temp = brandProvider.applyFilter(temp);
		}
		if (sizeProvider != providerToUpdate) {
			temp = sizeProvider.applyFilter(temp);
		}
		if (colorProvider != providerToUpdate) {
			temp = colorProvider.applyFilter(temp);
		}
		temp = nameProvider.applyFilter(temp);
		providerToUpdate.update(temp);
	}

}
