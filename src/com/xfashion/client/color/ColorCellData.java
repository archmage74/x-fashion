package com.xfashion.client.color;

import com.xfashion.client.FilterCellData;


public class ColorCellData extends FilterCellData {

	public static String ICON_PREFIX_COLOR = "color";
	
	public ColorCellData(String name, boolean available) {
		setName(name);
		setAvailable(available);
		setIconPrefix(ICON_PREFIX_COLOR);
	}
	
}
