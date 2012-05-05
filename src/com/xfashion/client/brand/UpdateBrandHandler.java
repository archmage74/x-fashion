package com.xfashion.client.brand;

import com.xfashion.client.FilterDataEventHandler;

public interface UpdateBrandHandler extends FilterDataEventHandler {
	
	void onUpdateBrand(UpdateBrandEvent event);
	
}
