package com.xfashion.client.brand;

import com.xfashion.client.FilterDataEventHandler;

public interface DeleteBrandHandler extends FilterDataEventHandler {
	
	void onDeleteBrand(DeleteBrandEvent event);
	
}
