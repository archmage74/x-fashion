package com.xfashion.client;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.xfashion.shared.CategoryDTO;

public abstract class FilterCellData {

	private String name;

	private boolean available;
	
	private boolean selected;
	
	private String iconPrefix;
	
	public FilterCellData() {
		this.selected = false;
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

	public void render(SafeHtmlBuilder sb, CategoryDTO selectedCategory) {
		if (isSelected()) {
			if (isAvailable()) {
				sb.appendHtmlConstant("<img class=\"filterIconEnabled\" src=\"" + iconPrefix + "IconSelected.png\" width=\"22\" height=\"20\"></img>");
			} else {
				sb.appendHtmlConstant("<img class=\"filterIconDisabled\" src=\"whitePixel.png\" width=\"22\" height=\"20\"></img>");
			}
			String style = createSelectedStyle(selectedCategory);
			sb.appendHtmlConstant("<div class=\"filterLabelSelected\" style=\"" + style + "\">");
		} else {
			if (isAvailable()) {
				sb.appendHtmlConstant("<img class=\"filterIconEnabled\" src=\"" + iconPrefix + "IconUnselected.png\" width=\"22\" height=\"20\"></img>");
				sb.appendHtmlConstant("<div class=\"filterLabelUnselected\">");
			} else {
				sb.appendHtmlConstant("<img class=\"filterIconDisabled\" src=\"whitePixel.png\" width=\"22\" height=\"20\"></img>");
				sb.appendHtmlConstant("<div class=\"filterLabelDisabled\">");
			}
		}
		sb.appendHtmlConstant(getName());
		sb.appendHtmlConstant("</div>");
	}
	
	private String createSelectedStyle(CategoryDTO selectedCategory) {
		String borderColor = selectedCategory.getBorderColor();
		String backgroundColor = selectedCategory.getBackgroundColor();
		String style = "background-color: " + backgroundColor + "; " +
			"border-top: 2px solid " + borderColor + "; " +
			"border-right: 2px solid " + borderColor + "; " +
			"border-bottom: 2px solid " + borderColor + "; ";
		return style;
	}

	public String getIconPrefix() {
		return iconPrefix;
	}

	public void setIconPrefix(String iconPrefix) {
		this.iconPrefix = iconPrefix;
	}

}
