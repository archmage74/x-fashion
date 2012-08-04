package com.xfashion.client.at.category.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.CategoryDTO;

public class CreateCategoryEvent extends FilterDataEvent<CreateCategoryHandler, CategoryDTO> {

	public static Type<CreateCategoryHandler> TYPE = new Type<CreateCategoryHandler>();
	
	public CreateCategoryEvent(CategoryDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<CreateCategoryHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreateCategoryHandler handler) {
		handler.onCreateCategory(this);
	}

}
