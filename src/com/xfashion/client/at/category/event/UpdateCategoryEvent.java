package com.xfashion.client.at.category.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.CategoryDTO;

public class UpdateCategoryEvent extends FilterDataEvent<UpdateCategoryHandler, CategoryDTO> {
	
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
