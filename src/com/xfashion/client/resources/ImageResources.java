package com.xfashion.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;

public interface ImageResources extends ClientBundle {
	
	@Source("icon-showtools.png")
	@ImageOptions(width=23, height=23)
	ImageResource iconShowTools();
	
	@Source("icon-editbulk.png")
	@ImageOptions(width=23, height=23)
	ImageResource iconEditBulk();
	
	@Source("icon-minimize.png")
	@ImageOptions(width=12, height=23)
	ImageResource iconMinimize();
	
	@Source("icon-maximize.png")
	@ImageOptions(width=12, height=23)
	ImageResource iconMaximize();

	@Source("icon-clearnotepad.png")
	@ImageOptions(width=23, height=23)
	ImageResource iconClearnotepad();

	@Source("icon-open.png")
	@ImageOptions(width=23, height=23)
	ImageResource iconOpen();

	@Source("icon-save.png")
	@ImageOptions(width=23, height=23)
	ImageResource iconSave();

	@Source("icon-deliverynotice.png")
	@ImageOptions(width=23, height=23)
	ImageResource iconDeliverynotice();

	@Source("icon-printsticker.png")
	@ImageOptions(width=23, height=23)
	ImageResource iconPrintsticker();

	@Source("icon-scanbarcode.png")
	@ImageOptions(width=23, height=23)
	ImageResource iconScanbarcode();
	
	@Source("icon-intostock.png")
	@ImageOptions(width=23, height=23)
	ImageResource iconIntostock();

	@Source("icon-minwin.png")
	@ImageOptions(width=23, height=23)
	ImageResource iconMinwin();

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
	
	@Source("pixel-transparent.png")
	@ImageOptions(width=22, height=20)
	ImageResource iconNotAvailable();
	
	@Source("icon-sizeUnselected.png")
	@ImageOptions(width=22, height=20)
	ImageResource iconSizeUnselected();
	
	@Source("icon-sizeSelected.png")
	@ImageOptions(width=22, height=20)
	ImageResource iconSizeSelected();
	
	@Source("icon-colorUnselected.png")
	@ImageOptions(width=22, height=20)
	ImageResource iconColorUnselected();
	
	@Source("icon-colorSelected.png")
	@ImageOptions(width=22, height=20)
	ImageResource iconColorSelected();
	
	@Source("icon-brandUnselected.png")
	@ImageOptions(width=22, height=20)
	ImageResource iconBrandUnselected();
	
	@Source("icon-brandSelected.png")
	@ImageOptions(width=22, height=20)
	ImageResource iconBrandSelected();
	
	@Source("icon-styleUnselected.png")
	@ImageOptions(width=22, height=20)
	ImageResource iconStyleUnselected();
	
	@Source("icon-styleSelected.png")
	@ImageOptions(width=22, height=20)
	ImageResource iconStyleSelected();

}
