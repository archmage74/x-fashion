package com.xfashion.client.at.size.event;

import com.xfashion.client.FilterDataEventHandler;

public interface CreateSizeHandler extends FilterDataEventHandler {
	
	void onCreateSize(CreateSizeEvent event);
	
}
