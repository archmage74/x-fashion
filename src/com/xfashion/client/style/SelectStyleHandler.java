package com.xfashion.client.style;

import com.xfashion.client.FilterDataEventHandler;

public interface SelectStyleHandler extends FilterDataEventHandler {
	
	void onSelectStyle(SelectStyleEvent event);
	
}
