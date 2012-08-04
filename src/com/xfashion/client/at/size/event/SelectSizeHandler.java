package com.xfashion.client.at.size.event;

import com.xfashion.client.FilterDataEventHandler;

public interface SelectSizeHandler extends FilterDataEventHandler {
	
	void onSelectSize(SelectSizeEvent event);
	
}
