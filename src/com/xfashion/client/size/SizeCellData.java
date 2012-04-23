package com.xfashion.client.size;

import com.xfashion.shared.FilterCellData;


public class SizeCellData extends FilterCellData {

	public static String ICON_PREFIX_SIZE = "size";
	
	public SizeCellData(String name) {
		setName(name);
		setIconPrefix(ICON_PREFIX_SIZE);
	}
	
}
