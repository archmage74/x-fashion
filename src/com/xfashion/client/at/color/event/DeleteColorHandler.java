package com.xfashion.client.at.color.event;

import com.xfashion.client.FilterDataEventHandler;

public interface DeleteColorHandler extends FilterDataEventHandler {
	
	void onDeleteColor(DeleteColorEvent event);
	
}
