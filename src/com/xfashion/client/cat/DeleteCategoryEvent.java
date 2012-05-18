package com.xfashion.client.cat;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.CategoryDTO;

public class DeleteCategoryEvent extends FilterDataEvent2<DeleteCategoryHandler, CategoryDTO> {

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
