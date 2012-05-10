package com.xfashion.client.at;

import com.xfashion.client.brand.BrandDataProvider;
import com.xfashion.client.cat.CategoryDataProvider;
import com.xfashion.client.color.ColorDataProvider;
import com.xfashion.client.size.SizeDataProvider;

public interface ProvidesArticleFilter {
	CategoryDataProvider getCategoryProvider();
	BrandDataProvider getBrandProvider();
	SizeDataProvider getSizeProvider();
	ColorDataProvider getColorProvider();
}
