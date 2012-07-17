package com.xfashion.client.size.event;

import com.xfashion.client.FilterDataEventHandler;

public interface MoveDownSizeHandler extends FilterDataEventHandler {
	
	void onMoveDownSize(MoveDownSizeEvent event);
	
}
