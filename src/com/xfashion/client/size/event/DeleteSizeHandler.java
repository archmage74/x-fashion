package com.xfashion.client.size.event;

import com.xfashion.client.FilterDataEventHandler;

public interface DeleteSizeHandler extends FilterDataEventHandler {
	
	void onDeleteSize(DeleteSizeEvent event);
	
}
