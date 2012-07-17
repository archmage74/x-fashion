package com.xfashion.client.cat.event;

import com.xfashion.client.FilterDataEventHandler;

public interface CreateCategoryHandler extends FilterDataEventHandler {
	
	void onCreateCategory(CreateCategoryEvent event);
	
}
