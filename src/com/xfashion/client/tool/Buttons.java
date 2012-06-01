package com.xfashion.client.tool;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.xfashion.client.resources.ImageResources;

public class Buttons {

	private static ImageResources images = GWT.<ImageResources>create(ImageResources.class);
	
	public static Image showTools() {
		Image icon = new Image(images.iconShowTools());
		icon.setStyleName("buttonShowTools");
		return icon;
	}
	
	public static Image editBulk() {
		Image icon = new Image(images.iconEditBulk());
		icon.setStyleName("buttonShowTools");
		return icon;
	}
	
	public static Image minimize() {
		Image icon = new Image(images.iconMinimize());
		icon.setStyleName("buttonMinMax");
		return icon;
	}
	
	public static Image maximize() {
		Image icon = new Image(images.iconMaximize());
		icon.setStyleName("buttonMinMax");
		return icon;
	}
	
	public static Image deliverynotice() {
		Image icon = new Image(images.iconDeliverynotice());
		icon.setStyleName("buttonShowTools");
		return icon;
	}
	
	public static Image clearnotepad() {
		Image icon = new Image(images.iconClearnotepad());
		icon.setStyleName("buttonShowTools");
		return icon;
	}
	
	public static Image open() {
		Image icon = new Image(images.iconOpen());
		icon.setStyleName("buttonShowTools");
		return icon;
	}
	
	public static Image save() {
		Image icon = new Image(images.iconSave());
		icon.setStyleName("buttonShowTools");
		return icon;
	}
	
	public static Image printsticker() {
		Image icon = new Image(images.iconPrintsticker());
		icon.setStyleName("buttonShowTools");
		return icon;
	}
	
	public static Image scanbarcode() {
		Image icon = new Image(images.iconScanbarcode());
		icon.setStyleName("buttonShowTools");
		return icon;
	}
	
	public static Image intostock() {
		Image icon = new Image(images.iconIntostock());
		icon.setStyleName("buttonShowTools");
		return icon;
	}
	
	public static Image edit() {
		Image icon = new Image(images.iconEdit());
		icon.setStyleName("buttonTool");
		return icon;
	}
	
	public static Image delete() {
		Image icon = new Image(images.iconDelete());
		icon.setStyleName("buttonTool");
		return icon;
	}
	
	public static Image up() {
		Image icon = new Image(images.iconUp());
		icon.setStyleName("buttonTool");
		return icon;
	}
	
	public static Image down() {
		Image icon = new Image(images.iconDown());
		icon.setStyleName("buttonTool");
		return icon;
	}
	
}
