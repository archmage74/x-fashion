package com.xfashion.client.style;

import com.xfashion.client.FilterDataEventHandler;

public interface CreateStyleHandler extends FilterDataEventHandler {
	
	void onCreateStyle(CreateStyleEvent event);
	
}
