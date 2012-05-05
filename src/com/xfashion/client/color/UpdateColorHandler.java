package com.xfashion.client.color;

import com.xfashion.client.FilterDataEventHandler;

public interface UpdateColorHandler extends FilterDataEventHandler {
	
	void onUpdateColor(UpdateColorEvent event);
	
}
