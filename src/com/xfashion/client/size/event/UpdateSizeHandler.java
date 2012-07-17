package com.xfashion.client.size.event;

import com.xfashion.client.FilterDataEventHandler;

public interface UpdateSizeHandler extends FilterDataEventHandler {
	
	void onUpdateSize(UpdateSizeEvent event);
	
}
