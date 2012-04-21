package com.xfashion.client.style;

import com.xfashion.client.FilterCellData;


public class StyleCellData extends FilterCellData {

	public static String ICON_PREFIX_STYLE = "style";
	
	public StyleCellData(String name, boolean available) {
		setName(name);
		setAvailable(available);
		setIconPrefix(ICON_PREFIX_STYLE);
	}
	
}
