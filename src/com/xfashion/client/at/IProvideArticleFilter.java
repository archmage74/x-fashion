package com.xfashion.client.at;

import com.xfashion.client.at.brand.BrandDataProvider;
import com.xfashion.client.at.category.CategoryDataProvider;
import com.xfashion.client.at.color.ColorDataProvider;
import com.xfashion.client.at.name.NameDataProvider;
import com.xfashion.client.at.size.SizeDataProvider;

public interface IProvideArticleFilter extends IFilterArticle {

	public CategoryDataProvider getCategoryProvider();

	public BrandDataProvider getBrandProvider();

	public SizeDataProvider getSizeProvider();

	public ColorDataProvider getColorProvider();

	public NameDataProvider getNameProvider();

	public void updateProviders();

}
