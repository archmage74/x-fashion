package com.xfashion.client.cat;

import com.xfashion.client.FilterDataEventHandler;

public interface ChooseCategoryAndStyleHandler extends FilterDataEventHandler {
	
	void onChooseCategoryAndStyle(ChooseCategoryAndStyleEvent event);
	
}
