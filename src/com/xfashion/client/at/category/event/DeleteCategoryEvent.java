package com.xfashion.client.at.category.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.CategoryDTO;

public class DeleteCategoryEvent extends FilterDataEvent<DeleteCategoryHandler, CategoryDTO> {

	public static Type<DeleteCategoryHandler> TYPE = new Type<DeleteCategoryHandler>();
	
	public DeleteCategoryEvent(CategoryDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<DeleteCategoryHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeleteCategoryHandler handler) {
		handler.onDeleteCategory(this);
	}

}
