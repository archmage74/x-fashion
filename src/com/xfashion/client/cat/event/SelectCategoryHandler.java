package com.xfashion.client.cat.event;

import com.xfashion.client.FilterDataEventHandler;

public interface SelectCategoryHandler extends FilterDataEventHandler {
	
	void onSelectCategory(SelectCategoryEvent event);
	
}
