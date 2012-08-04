package com.xfashion.client.at.category.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.CategoryDTO;

public class SelectCategoryEvent extends FilterDataEvent<SelectCategoryHandler, CategoryDTO> {

	public static Type<SelectCategoryHandler> TYPE = new Type<SelectCategoryHandler>();
	
	public SelectCategoryEvent(CategoryDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<SelectCategoryHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SelectCategoryHandler handler) {
		handler.onSelectCategory(this);
	}

}
