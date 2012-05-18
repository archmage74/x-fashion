package com.xfashion.client.brand;

import com.xfashion.client.FilterDataEventHandler;

public interface ChooseBrandHandler extends FilterDataEventHandler {
	
	void onChooseBrand(ChooseBrandEvent event);
	
}
