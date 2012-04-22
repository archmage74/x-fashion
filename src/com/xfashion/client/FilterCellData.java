package com.xfashion.client;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.xfashion.shared.CategoryDTO;

public abstract class FilterCellData {
	
	private String name;
	
	private boolean available;
	
	private boolean selected;
	
	private Integer articleAmount;
	
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


	public String getIconPrefix() {
		return iconPrefix;
	}

	public void setIconPrefix(String iconPrefix) {
		this.iconPrefix = iconPrefix;
	}

	public Integer getArticleAmount() {
		return articleAmount;
	}

	public void setArticleAmount(Integer articleAmount) {
		this.articleAmount = articleAmount;
	}

	public void render(SafeHtmlBuilder sb, CategoryDTO selectedCategory) {
		if (isSelected()) {
			String style = createSelectedStyle(selectedCategory);
			if (isAvailable()) {
				sb.appendHtmlConstant("<img class=\"filterIconEnabled\" src=\"" + getIconPrefix() + "IconSelected.png\" width=\"22\" height=\"20\"></img>");
			} else {
				sb.appendHtmlConstant("<img class=\"filterIconDisabled\" src=\"whitePixel.png\" width=\"22\" height=\"20\"></img>");
			}
			sb.appendHtmlConstant("<table cellspacing=\"0\" cellpadding=\"0\" class=\"filterTable\" style=\"" + style + "\">");
			sb.appendHtmlConstant("<tr><td class=\"filterText filterLabelSelected\">");
		} else {
			if (isAvailable()) {
				sb.appendHtmlConstant("<img class=\"filterIconEnabled\" src=\"" + getIconPrefix() + "IconUnselected.png\" width=\"22\" height=\"20\"></img>");
				sb.appendHtmlConstant("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"filterTable\">");
				sb.appendHtmlConstant("<tr><td class=\"filterText filterLabelUnselected\">");
			} else {
				sb.appendHtmlConstant("<img class=\"filterIconDisabled\" src=\"whitePixel.png\" width=\"22\" height=\"20\"></img>");
				sb.appendHtmlConstant("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"filterTable\">");
				sb.appendHtmlConstant("<tr><td class=\"filterText filterLabelDisabled\">");
			}
		}
		sb.appendHtmlConstant(getName());
		if (isSelected()) {
			sb.appendHtmlConstant("</td><td class=\"filterText filterAmountSelected\">");
		} else {
			sb.appendHtmlConstant("</td><td class=\"filterText filterAmountUnselected\">");
		}
		if (getArticleAmount() != null) {
			sb.appendHtmlConstant(getArticleAmount().toString());
		}
		sb.appendHtmlConstant("</td></tr></table>");
	}
	
	private String createSelectedStyle(CategoryDTO selectedCategory) {
		String style;
		String borderColor;
		String backgroundColor;
		if (selectedCategory != null) {
			borderColor = selectedCategory.getBorderColor();
			backgroundColor = selectedCategory.getBackgroundColor();
		} else {
			borderColor = "#777;";
			backgroundColor = "#aaa";
		}
		style = "background-color: " + backgroundColor + "; " +
			"border-top: 2px solid " + borderColor + "; " +
			"border-right: 2px solid " + borderColor + "; " +
			"border-bottom: 2px solid " + borderColor + "; ";
		return style;
	}

}
