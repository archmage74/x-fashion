package com.xfashion.client.at;

import com.xfashion.client.brand.BrandDataProvider;
import com.xfashion.client.cat.CategoryDataProvider;
import com.xfashion.client.color.ColorDataProvider;
import com.xfashion.client.size.SizeDataProvider;
import com.xfashion.client.style.StyleDataProvider;

public interface ProvidesArticleFilter {
	CategoryDataProvider getCategoryProvider();
	StyleDataProvider getStyleProvider();
	BrandDataProvider getBrandProvider();
	SizeDataProvider getSizeProvider();
	ColorDataProvider getColorProvider();
}
