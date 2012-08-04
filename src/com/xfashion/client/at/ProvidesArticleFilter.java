package com.xfashion.client.at;

import com.xfashion.client.at.brand.BrandDataProvider;
import com.xfashion.client.at.category.CategoryDataProvider;
import com.xfashion.client.at.color.ColorDataProvider;
import com.xfashion.client.at.size.SizeDataProvider;

public interface ProvidesArticleFilter {
	CategoryDataProvider getCategoryProvider();
	BrandDataProvider getBrandProvider();
	SizeDataProvider getSizeProvider();
	ColorDataProvider getColorProvider();
}
