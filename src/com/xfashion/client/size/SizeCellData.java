package com.xfashion.client.size;

import com.xfashion.client.FilterCellData;


public class SizeCellData extends FilterCellData {

	public static String ICON_PREFIX_SIZE = "size";
	
	public SizeCellData(String name, boolean available) {
		setName(name);
		setAvailable(available);
		setIconPrefix(ICON_PREFIX_SIZE);
	}
	
}
