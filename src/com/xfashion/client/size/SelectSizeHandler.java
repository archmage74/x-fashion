package com.xfashion.client.size;

import com.xfashion.client.FilterDataEventHandler;

public interface SelectSizeHandler extends FilterDataEventHandler {
	
	void onSelectSize(SelectSizeEvent event);
	
}
