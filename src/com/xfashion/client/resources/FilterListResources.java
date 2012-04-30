package com.xfashion.client.resources;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellList.Style;

public interface FilterListResources extends CellList.Resources {

	@Source("filterList.css")
	@Override
	Style cellListStyle();
	
}
