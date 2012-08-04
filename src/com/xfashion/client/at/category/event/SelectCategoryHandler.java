package com.xfashion.client.at.category.event;

import com.xfashion.client.FilterDataEventHandler;

public interface SelectCategoryHandler extends FilterDataEventHandler {
	
	void onSelectCategory(SelectCategoryEvent event);
	
}
