package com.xfashion.client.at.category.event;

import com.xfashion.client.FilterDataEventHandler;

public interface ChooseCategoryAndStyleHandler extends FilterDataEventHandler {
	
	void onChooseCategoryAndStyle(ChooseCategoryAndStyleEvent event);
	
}
