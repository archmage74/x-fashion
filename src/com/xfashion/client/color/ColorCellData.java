package com.xfashion.client.color;

import com.xfashion.shared.FilterCellData;


public class ColorCellData extends FilterCellData {

	public static String ICON_PREFIX_COLOR = "color";
	
	public ColorCellData(String name) {
		setName(name);
		setIconPrefix(ICON_PREFIX_COLOR);
	}
	
}
