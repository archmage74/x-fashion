package com.xfashion.client.style.event;

import com.xfashion.client.FilterDataEventHandler;

public interface CreateStyleHandler extends FilterDataEventHandler {
	
	void onCreateStyle(CreateStyleEvent event);
	
}
