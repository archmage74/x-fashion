package com.xfashion.client.cat.event;

import com.xfashion.client.FilterDataEventHandler;

public interface ChooseCategoryAndStyleHandler extends FilterDataEventHandler {
	
	void onChooseCategoryAndStyle(ChooseCategoryAndStyleEvent event);
	
}
