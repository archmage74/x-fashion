package com.xfashion.client.at.color.event;

import com.xfashion.client.FilterDataEventHandler;

public interface SelectColorHandler extends FilterDataEventHandler {
	
	void onSelectColor(SelectColorEvent event);
	
}
