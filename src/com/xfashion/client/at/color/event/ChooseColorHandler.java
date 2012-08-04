package com.xfashion.client.at.color.event;

import com.xfashion.client.FilterDataEventHandler;

public interface ChooseColorHandler extends FilterDataEventHandler {
	
	void onChooseColor(ChooseColorEvent event);
	
}
