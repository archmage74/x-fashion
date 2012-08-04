package com.xfashion.client.at.category.event;

import com.xfashion.client.FilterDataEventHandler;

public interface CreateCategoryHandler extends FilterDataEventHandler {
	
	void onCreateCategory(CreateCategoryEvent event);
	
}
