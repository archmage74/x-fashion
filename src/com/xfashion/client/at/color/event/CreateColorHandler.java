package com.xfashion.client.at.color.event;

import com.xfashion.client.FilterDataEventHandler;

public interface CreateColorHandler extends FilterDataEventHandler {
	
	void onCreateColor(CreateColorEvent event);
	
}
