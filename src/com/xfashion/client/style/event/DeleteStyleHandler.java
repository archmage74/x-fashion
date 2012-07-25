package com.xfashion.client.style.event;

import com.xfashion.client.FilterDataEventHandler;

public interface DeleteStyleHandler extends FilterDataEventHandler {
	
	void onDeleteStyle(DeleteStyleEvent event);
	
}
