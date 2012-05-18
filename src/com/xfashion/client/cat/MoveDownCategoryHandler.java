package com.xfashion.client.cat;

import com.xfashion.client.FilterDataEventHandler;

public interface MoveDownCategoryHandler extends FilterDataEventHandler {
	
	void onMoveDownCategory(MoveDownCategoryEvent event);
	
}
