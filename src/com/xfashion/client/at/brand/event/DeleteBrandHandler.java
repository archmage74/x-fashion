package com.xfashion.client.at.brand.event;

import com.xfashion.client.FilterDataEventHandler;

public interface DeleteBrandHandler extends FilterDataEventHandler {
	
	void onDeleteBrand(DeleteBrandEvent event);
	
}
