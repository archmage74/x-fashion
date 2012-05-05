package com.xfashion.client.brand;

import com.xfashion.client.FilterDataEventHandler;

public interface CreateBrandHandler extends FilterDataEventHandler {
	
	void onCreateBrand(CreateBrandEvent event);
	
}
