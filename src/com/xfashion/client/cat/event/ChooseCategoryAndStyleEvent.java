package com.xfashion.client.cat.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.StyleDTO;

public class ChooseCategoryAndStyleEvent extends Event<ChooseCategoryAndStyleHandler> {

	public static Type<ChooseCategoryAndStyleHandler> TYPE = new Type<ChooseCategoryAndStyleHandler>();
	
	CategoryDTO category;
	StyleDTO style;
	
	public ChooseCategoryAndStyleEvent(CategoryDTO category, StyleDTO style) {
		this.category = category;
		this.style = style;
	}
	
	@Override
	public Type<ChooseCategoryAndStyleHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChooseCategoryAndStyleHandler handler) {
		handler.onChooseCategoryAndStyle(this);
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public StyleDTO getStyle() {
		return style;
	}

}
