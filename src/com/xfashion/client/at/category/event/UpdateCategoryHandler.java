package com.xfashion.client.at.category.event;

import com.xfashion.client.FilterDataEventHandler;

public interface UpdateCategoryHandler extends FilterDataEventHandler {
	
	void onUpdateCategory(UpdateCategoryEvent event);
	
}
