package com.xfashion.client.cat.event;

import com.xfashion.client.FilterDataEventHandler;

public interface UpdateCategoryHandler extends FilterDataEventHandler {
	
	void onUpdateCategory(UpdateCategoryEvent event);
	
}
