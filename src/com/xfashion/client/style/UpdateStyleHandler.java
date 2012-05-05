package com.xfashion.client.style;

import com.xfashion.client.FilterDataEventHandler;

public interface UpdateStyleHandler extends FilterDataEventHandler {
	
	void onUpdateStyle(UpdateStyleEvent event);
	
}
