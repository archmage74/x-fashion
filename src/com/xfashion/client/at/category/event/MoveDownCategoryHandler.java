package com.xfashion.client.at.category.event;

import com.xfashion.client.FilterDataEventHandler;

public interface MoveDownCategoryHandler extends FilterDataEventHandler {
	
	void onMoveDownCategory(MoveDownCategoryEvent event);
	
}
