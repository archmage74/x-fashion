package com.xfashion.client.at.category.event;

import com.xfashion.client.FilterDataEventHandler;

public interface DeleteCategoryHandler extends FilterDataEventHandler {
	
	void onDeleteCategory(DeleteCategoryEvent event);
	
}
