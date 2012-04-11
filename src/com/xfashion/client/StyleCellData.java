package com.xfashion.client;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class StyleCellData {
	
	private String name;

	private boolean available;
	
	private boolean selected;
	
	public StyleCellData(String name, boolean available) {
		this.name = name;
		this.available = available;
		this.selected = false;
	}
	
	public void render(SafeHtmlBuilder sb) {
		if (selected) {
			if (available) {
				sb.appendHtmlConstant("<img class=\"filterIconEnabled\" src=\"styleIconSelected.png\" width=\"22\" height=\"20\"></img>");
			} else {
				sb.appendHtmlConstant("<img class=\"filterIconDisabled\" src=\"whitePixel.png\" width=\"22\" height=\"20\"></img>");
			}
			sb.appendHtmlConstant("<div class=\"filterLabelSelected\">"); // style=\"height:20px; margin-left:3px; margin-right:3px; font-size:12px; padding-top:4px;\">");
		} else {
			if (available) {
				sb.appendHtmlConstant("<img class=\"filterIconEnabled\" src=\"styleIconUnselected.png\" width=\"22\" height=\"20\"></img>");
				sb.appendHtmlConstant("<div class=\"filterLabelUnselected\">"); // style=\"height:20px; margin-left:3px; margin-right:3px; font-size:12px; padding-top:4px;\">");
			} else {
				sb.appendHtmlConstant("<img class=\"filterIconDisabled\" src=\"whitePixel.png\" width=\"22\" height=\"20\"></img>");
				sb.appendHtmlConstant("<div class=\"filterLabelDisabled\">"); // style=\"color:#bfbfbf; height:20px; margin-left:3px; margin-right:3px; font-size:12px; padding-top:4px;\">");
			}
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
