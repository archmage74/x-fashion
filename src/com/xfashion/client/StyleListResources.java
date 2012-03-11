package com.xfashion.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource.ImportedWithPrefix;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellList.Style;

public interface StyleListResources extends CellList.Resources {

	// public static StyleListResources INSTANCE =
	// GWT.create(StyleListResources.class);

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

//	@Source(StyleListStyle.DEFAULT_CSS)

	@Source("styleList.css")
	@Override
	Style cellListStyle();
}
