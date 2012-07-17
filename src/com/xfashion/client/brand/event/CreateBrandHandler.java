package com.xfashion.client.brand.event;

import com.xfashion.client.FilterDataEventHandler;

public interface CreateBrandHandler extends FilterDataEventHandler {
	
	void onCreateBrand(CreateBrandEvent event);
	
}
