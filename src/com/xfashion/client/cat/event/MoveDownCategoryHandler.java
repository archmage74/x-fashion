package com.xfashion.client.cat.event;

import com.xfashion.client.FilterDataEventHandler;

public interface MoveDownCategoryHandler extends FilterDataEventHandler {
	
	void onMoveDownCategory(MoveDownCategoryEvent event);
	
}
