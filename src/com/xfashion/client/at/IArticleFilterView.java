package com.xfashion.client.at;

import com.xfashion.client.at.event.RefreshFilterEvent;

public interface IArticleFilterView {

	
	void init();
	
	public void onRefreshFilter(RefreshFilterEvent event);
	
	public void applyFilters();
	
	public void updateProviders();
	
	public void updateProvider(); // make the impl more general instead of updateStyleProvider(), ...
	
}
