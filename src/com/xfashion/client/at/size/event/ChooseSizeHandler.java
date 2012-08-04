package com.xfashion.client.at.size.event;

import com.xfashion.client.FilterDataEventHandler;

public interface ChooseSizeHandler extends FilterDataEventHandler {
	
	void onChooseSize(ChooseSizeEvent event);
	
}
