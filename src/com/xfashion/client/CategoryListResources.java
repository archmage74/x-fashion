package com.xfashion.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource.ImportedWithPrefix;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellList.Style;

public interface CategoryListResources extends CellList.Resources {

	// public static CategoryListResources INSTANCE = GWT.create(CategoryListResources.class);

	// @Source("footer_bg.png")
	// @ImageOptions(repeatStyle = RepeatStyle.Both, flipRtl = true)
	// ImageResource cellTableFooterBackground();
	//
	// @Source("header.png")
	// @ImageOptions(repeatStyle = RepeatStyle.Horizontal, flipRtl = true)
	// ImageResource cellTableHeaderBackground();
	//
	// @Source("table_head_bg_left.png")
	// @ImageOptions(repeatStyle = RepeatStyle.None, flipRtl = true)
	// ImageResource cellTableHeaderFirstColumnBackground();
	//
	// @Source("table_head_bg_right.png")
	// @ImageOptions(repeatStyle = RepeatStyle.None, flipRtl = true)
	// ImageResource cellTableHeaderLastColumnBackground();

	// @Source(CategoryListStyle.DEFAULT_CSS)
	@Source("categoryList.css")
	@Override
	Style cellListStyle();
}
