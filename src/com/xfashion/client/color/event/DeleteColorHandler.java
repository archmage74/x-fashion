package com.xfashion.client.color.event;

import com.xfashion.client.FilterDataEventHandler;

public interface DeleteColorHandler extends FilterDataEventHandler {
	
	void onDeleteColor(DeleteColorEvent event);
	
}
