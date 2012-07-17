package com.xfashion.client.brand.event;

import com.xfashion.client.FilterDataEventHandler;

public interface SelectBrandHandler extends FilterDataEventHandler {
	
	void onSelectBrand(SelectBrandEvent event);
	
}
