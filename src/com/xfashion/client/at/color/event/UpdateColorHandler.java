package com.xfashion.client.at.color.event;

import com.xfashion.client.FilterDataEventHandler;

public interface UpdateColorHandler extends FilterDataEventHandler {
	
	void onUpdateColor(UpdateColorEvent event);
	
}
