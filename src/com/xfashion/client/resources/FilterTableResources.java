package com.xfashion.client.resources;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTable.Style;

public interface FilterTableResources extends CellTable.Resources {

	@Source("filterTable.css")
	@Override
	public Style cellTableStyle(); 
	
}
