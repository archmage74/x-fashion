package com.xfashion.client.size;

import com.xfashion.client.FilterDataEventHandler;

public interface DeleteSizeHandler extends FilterDataEventHandler {
	
	void onDeleteSize(DeleteSizeEvent event);
	
}
