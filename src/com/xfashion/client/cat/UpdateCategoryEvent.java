package com.xfashion.client.cat;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.CategoryDTO;

public class UpdateCategoryEvent extends FilterDataEvent2<UpdateCategoryHandler, CategoryDTO> {
	
	public static Type<UpdateCategoryHandler> TYPE = new Type<UpdateCategoryHandler>();

	public UpdateCategoryEvent(CategoryDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<UpdateCategoryHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateCategoryHandler handler) {
		handler.onUpdateCategory(this);
	}

}
