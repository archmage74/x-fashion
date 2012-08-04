package com.xfashion.client.at.brand.event;

import com.xfashion.client.FilterDataEventHandler;

public interface ChooseBrandHandler extends FilterDataEventHandler {
	
	void onChooseBrand(ChooseBrandEvent event);
	
}
