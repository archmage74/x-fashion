package com.xfashion.client.resources;

import com.google.gwt.user.cellview.client.CellTable.Style;
import com.google.gwt.user.cellview.client.CellTable;

public interface ArticleTableResources extends CellTable.Resources {

	@Source("articleTable.css")
	@Override
	Style cellTableStyle();
	
}
