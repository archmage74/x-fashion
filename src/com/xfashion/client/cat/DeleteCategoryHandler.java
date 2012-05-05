package com.xfashion.client.cat;

import com.xfashion.client.FilterDataEventHandler;

public interface DeleteCategoryHandler extends FilterDataEventHandler {
	
	void onDeleteCategory(DeleteCategoryEvent event);
	
}
