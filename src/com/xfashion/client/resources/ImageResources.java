package com.xfashion.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;

public interface ImageResources extends ClientBundle {
	
	@Source("icon-showtools.png")
	@ImageOptions(width=23, height=23)
	ImageResource iconShowTools();
	
	@Source("icon-delete.png")
	@ImageOptions(width=10, height=11)
	ImageResource iconDelete();
	
	@Source("icon-edit.png")
	@ImageOptions(width=10, height=11)
	ImageResource iconEdit();
	
	@Source("icon-up.png")
	@ImageOptions(width=10, height=11)
	ImageResource iconUp();
	
	@Source("icon-down.png")
	@ImageOptions(width=10, height=11)
	ImageResource iconDown();
	
}
