package com.xfashion.client.at.brand.event;

import com.xfashion.client.FilterDataEventHandler;

public interface SelectBrandHandler extends FilterDataEventHandler {
	
	void onSelectBrand(SelectBrandEvent event);
	
}
