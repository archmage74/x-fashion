package com.xfashion.client.size.event;

import com.xfashion.client.FilterDataEventHandler;

public interface CreateSizeHandler extends FilterDataEventHandler {
	
	void onCreateSize(CreateSizeEvent event);
	
}
