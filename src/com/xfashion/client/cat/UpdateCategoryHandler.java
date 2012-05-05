package com.xfashion.client.cat;

import com.xfashion.client.FilterDataEventHandler;

public interface UpdateCategoryHandler extends FilterDataEventHandler {
	
	void onUpdateCategory(UpdateCategoryEvent event);
	
}
