package com.xfashion.client.brand;

import com.xfashion.client.FilterCellData;


public class BrandCellData extends FilterCellData {
	
	public static String ICON_PREFIX_BRAND = "brand";
	
	public BrandCellData(String name, boolean available) {
		setName(name);
		setAvailable(available);
		setIconPrefix(ICON_PREFIX_BRAND);
	}

}
