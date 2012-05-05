package com.xfashion.client.style;

import com.xfashion.client.FilterDataEventHandler;

public interface DeleteStyleHandler extends FilterDataEventHandler {
	
	void onDeleteStyle(DeleteStyleEvent event);
	
}
