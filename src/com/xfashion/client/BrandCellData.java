package com.xfashion.client;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class BrandCellData {
	
	private String name;

	private boolean available;
	
	private boolean selected;
	
	public BrandCellData(String name, boolean available) {
		this.name = name;
		this.available = available;
	}
	
	public void render(SafeHtmlBuilder sb) {
		if (available) {
			sb.appendHtmlConstant("<div style=\"height:20px; margin-left:3px; margin-right:3px; font-size:12px; padding-top:4px;\">");
		} else {
			sb.appendHtmlConstant("<div style=\"color:#888; height:20px; margin-left:3px; margin-right:3px; font-size:12px; padding-top:4px;\">");
		}
		sb.appendHtmlConstant(name);
		sb.appendHtmlConstant("</div>");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
