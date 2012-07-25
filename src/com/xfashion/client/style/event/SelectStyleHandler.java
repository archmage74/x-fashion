package com.xfashion.client.style.event;

import com.xfashion.client.FilterDataEventHandler;

public interface SelectStyleHandler extends FilterDataEventHandler {
	
	void onSelectStyle(SelectStyleEvent event);
	
}
