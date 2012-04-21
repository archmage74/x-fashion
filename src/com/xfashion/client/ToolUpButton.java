package com.xfashion.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.xfashion.client.resources.ImageResources;

public class ToolUpButton extends Button {
	
	public ToolUpButton() {
		super();
		ImageResources images = GWT.<ImageResources>create(ImageResources.class);
		Image icon = new Image(images.iconUp());
		icon.setStyleName("iconToolButton");
		DOM.insertBefore(getElement(), icon.getElement(), DOM.getFirstChild(getElement()));
		this.setStyleName("buttonTool");
	}

}
