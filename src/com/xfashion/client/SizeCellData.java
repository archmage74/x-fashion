package com.xfashion.client;


public class SizeCellData extends FilterCellData {

	public static String ICON_PREFIX_SIZE = "size";
	
	public SizeCellData(String name, boolean available) {
		setName(name);
		setAvailable(available);
		setIconPrefix(ICON_PREFIX_SIZE);
	}
	
}
