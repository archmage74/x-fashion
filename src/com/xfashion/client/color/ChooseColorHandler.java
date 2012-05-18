package com.xfashion.client.color;

import com.xfashion.client.FilterDataEventHandler;

public interface ChooseColorHandler extends FilterDataEventHandler {
	
	void onChooseColor(ChooseColorEvent event);
	
}
