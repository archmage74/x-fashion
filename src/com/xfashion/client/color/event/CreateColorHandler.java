package com.xfashion.client.color.event;

import com.xfashion.client.FilterDataEventHandler;

public interface CreateColorHandler extends FilterDataEventHandler {
	
	void onCreateColor(CreateColorEvent event);
	
}
