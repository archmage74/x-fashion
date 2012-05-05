package com.xfashion.client.color;

import com.xfashion.client.FilterDataEventHandler;

public interface DeleteColorHandler extends FilterDataEventHandler {
	
	void onDeleteColor(DeleteColorEvent event);
	
}
