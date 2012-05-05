package com.xfashion.client.size;

import com.xfashion.client.FilterDataEventHandler;

public interface UpdateSizeHandler extends FilterDataEventHandler {
	
	void onUpdateSize(UpdateSizeEvent event);
	
}
