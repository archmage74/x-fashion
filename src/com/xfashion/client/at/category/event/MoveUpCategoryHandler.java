package com.xfashion.client.at.category.event;

import com.xfashion.client.FilterDataEventHandler;

public interface MoveUpCategoryHandler extends FilterDataEventHandler {
	
	void onMoveUpCategory(MoveUpCategoryEvent event);
	
}
