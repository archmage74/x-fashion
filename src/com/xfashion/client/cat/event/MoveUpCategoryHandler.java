package com.xfashion.client.cat.event;

import com.xfashion.client.FilterDataEventHandler;

public interface MoveUpCategoryHandler extends FilterDataEventHandler {
	
	void onMoveUpCategory(MoveUpCategoryEvent event);
	
}
