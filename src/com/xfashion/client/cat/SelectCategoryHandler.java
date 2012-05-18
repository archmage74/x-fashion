package com.xfashion.client.cat;

import com.xfashion.client.FilterDataEventHandler;

public interface SelectCategoryHandler extends FilterDataEventHandler {
	
	void onSelectCategory(SelectCategoryEvent event);
	
}
