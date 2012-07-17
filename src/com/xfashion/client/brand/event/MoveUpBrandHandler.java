package com.xfashion.client.brand.event;

import com.xfashion.client.FilterDataEventHandler;

public interface MoveUpBrandHandler extends FilterDataEventHandler {
	
	void onMoveUpBrand(MoveUpBrandEvent event);
	
}
