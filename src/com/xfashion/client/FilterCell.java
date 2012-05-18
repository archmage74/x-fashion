package com.xfashion.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.FilterCellData;

public class FilterCell<T extends FilterCellData<?>> extends AbstractCell<T> {
	
	private PanelMediator panelMediator;
	
	private FilterPanel<T> panel;
	
	public FilterCell(FilterPanel<T> panel, PanelMediator panelMediator) {
		super("change", "keydown");
		this.panel = panel;
		this.panelMediator = panelMediator;
	}
	
	@Override
	public void render(Context context, T cellData, SafeHtmlBuilder sb) {
		if (cellData == null) {
			return;
		}
		render(cellData, sb, panelMediator.getSelectedCategory());
	}

	private void render(T cellData, SafeHtmlBuilder sb, CategoryDTO selectedCategory) {
		if (cellData.isSelected()) {
			if (cellData.getArticleAmount() != null && cellData.getArticleAmount() > 0) {
				sb.appendHtmlConstant("<img class=\"filterIconEnabled\" src=\"" + cellData.getIconPrefix() + "IconSelected.png\" width=\"22\" height=\"20\"></img>");
			} else {
				sb.appendHtmlConstant("<img class=\"filterIconDisabled\" src=\"whitePixel.png\" width=\"22\" height=\"20\"></img>");
			}
			sb.appendHtmlConstant("<table cellspacing=\"0\" cellpadding=\"0\" class=\"filterTable\" >");
			sb.appendHtmlConstant("<tr><td class=\"filterText filterLabelSelected\">");
		} else {
			if (cellData.getArticleAmount() != null && cellData.getArticleAmount() > 0) {
				sb.appendHtmlConstant("<img class=\"filterIconEnabled\" src=\"" + cellData.getIconPrefix() + "IconUnselected.png\" width=\"22\" height=\"20\"></img>");
				sb.appendHtmlConstant("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"filterTable\">");
				sb.appendHtmlConstant("<tr><td class=\"filterText filterLabelUnselected\">");
			} else {
				sb.appendHtmlConstant("<img class=\"filterIconDisabled\" src=\"whitePixel.png\" width=\"22\" height=\"20\"></img>");
				sb.appendHtmlConstant("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"filterTable\">");
				sb.appendHtmlConstant("<tr><td class=\"filterText filterLabelDisabled\">");
			}
		}
		if (cellData.isInEditMode()) {
			sb.appendHtmlConstant("<input type=\"text\" value=\"" + cellData.getName() + "\" style=\"padding: 0px; margin: 0px; font-size: 12px; width: 65px; height: 14px; border: 1px inset;\" />");
		} else {
			sb.appendHtmlConstant(cellData.getName());
		}
		if (cellData.isSelected()) {
			sb.appendHtmlConstant("</td><td class=\"filterText filterAmountSelected\">");
		} else {
			sb.appendHtmlConstant("</td><td class=\"filterText filterAmountUnselected\">");
		}
		if (cellData.getArticleAmount() != null && cellData.getArticleAmount() > 0) {
			sb.appendHtmlConstant(cellData.getArticleAmount().toString());
		}
		sb.appendHtmlConstant("</td></tr></table>");
	}
	
	@Override
	public void onBrowserEvent(Context context, Element parent, T value, NativeEvent event, ValueUpdater<T> valueUpdater) {
		if (value == null) {
			return;
		}
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
		if ("change".equals(event.getType())) {
			// updateFromCell(parent, value);
		}
	}

	@Override
	public void onEnterKeyDown(Context context, Element parent, T value, NativeEvent event, ValueUpdater<T> valueUpdater) {
		if (value == null) {
			return;
		}
		updateFromCell(parent, value);
	}

	private void updateFromCell(Element parent, T value) {
		if (value.isInEditMode()) {
			value.setName(readElementName(parent));
			value.setInEditMode(false);
			panel.update(value);
		}
	}

	protected String readElementName(Element parent) {
		Node n = parent;
		n = n.getChild(1);
		n = n.getFirstChild();
		n = n.getFirstChild();
		n = n.getFirstChild();
		n = n.getFirstChild();
		
		InputElement e = parent.getChild(1).getFirstChild().getFirstChild().getFirstChild().getFirstChild().cast();
		return e.getValue();
	}


}
