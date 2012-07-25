package com.xfashion.client.style.event;

import com.xfashion.client.FilterDataEventHandler;

public interface UpdateStyleHandler extends FilterDataEventHandler {
	
	void onUpdateStyle(UpdateStyleEvent event);
	
}
