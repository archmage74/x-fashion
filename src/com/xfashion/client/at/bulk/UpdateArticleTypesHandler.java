package com.xfashion.client.at.bulk;

import com.xfashion.client.FilterDataEventHandler;

public interface UpdateArticleTypesHandler extends FilterDataEventHandler {
	
	void onUpdateArticleTypes(UpdateArticleTypesEvent event);
	
}
