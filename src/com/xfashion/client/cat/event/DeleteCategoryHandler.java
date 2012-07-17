package com.xfashion.client.cat.event;

import com.xfashion.client.FilterDataEventHandler;

public interface DeleteCategoryHandler extends FilterDataEventHandler {
	
	void onDeleteCategory(DeleteCategoryEvent event);
	
}
