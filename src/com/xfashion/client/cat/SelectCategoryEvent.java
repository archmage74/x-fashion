package com.xfashion.client.cat;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.CategoryDTO;

public class SelectCategoryEvent extends FilterDataEvent2<SelectCategoryHandler, CategoryDTO> {

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
