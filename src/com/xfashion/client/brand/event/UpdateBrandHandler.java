package com.xfashion.client.brand.event;

import com.xfashion.client.FilterDataEventHandler;

public interface UpdateBrandHandler extends FilterDataEventHandler {
	
	void onUpdateBrand(UpdateBrandEvent event);
	
}
