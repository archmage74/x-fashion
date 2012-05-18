package com.xfashion.client.cat;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.CategoryDTO;

public class CreateCategoryEvent extends FilterDataEvent2<CreateCategoryHandler, CategoryDTO> {

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
