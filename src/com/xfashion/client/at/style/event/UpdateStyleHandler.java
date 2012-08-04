package com.xfashion.client.at.style.event;

import com.xfashion.client.FilterDataEventHandler;

public interface UpdateStyleHandler extends FilterDataEventHandler {
	
	void onUpdateStyle(UpdateStyleEvent event);
	
}
