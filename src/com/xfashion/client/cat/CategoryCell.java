package com.xfashion.client.cat;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.xfashion.client.FilterCell;
import com.xfashion.client.PanelMediator;
import com.xfashion.shared.CategoryDTO;

class CategoryCell extends FilterCell<CategoryDTO> {

	public CategoryCell(CategoryPanel panel, PanelMediator panelMediator) {
		super(panel, panelMediator);
	}

	@Override
	protected String readElementName(Element parent) {
		InputElement e = parent.getFirstChild().getFirstChild().cast();
		return e.getValue();
	}

	@Override
	public void render(Context context, CategoryDTO category, SafeHtmlBuilder sb) {
		if (category == null) {
			return;
		}

		String css;
		String style;
		if (category.isSelected()) {
			css = "categorySelected";
			style = createSelectedStyle(category);
		} else {
			css = "categoryUnselected";
			style = "";
		}

		sb.appendHtmlConstant("<div class=\"" + css + "\" style=\"" + style + "\">");
		if (category.isInEditMode()) {
			sb.appendHtmlConstant("<input type=\"text\" value=\"" + category.getName() + "\" style=\"height: 19px; border: 1px inset;\" />");
		} else {
			sb.appendHtmlConstant(category.getName());
		}
		sb.appendHtmlConstant("</div>");
	}

	private String createSelectedStyle(CategoryDTO cellData) {
		CategoryDTO dto = cellData;
		String style = "background-color: " + dto.getBackgroundColor() + "; " + "border: 2px solid " + dto.getBorderColor() + ";";
		return style;
	}

}
