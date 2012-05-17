package com.xfashion.client.color;

import com.xfashion.client.FilterDataEventHandler;

public interface SelectColorHandler extends FilterDataEventHandler {
	
	void onSelectColor(SelectColorEvent event);
	
}
