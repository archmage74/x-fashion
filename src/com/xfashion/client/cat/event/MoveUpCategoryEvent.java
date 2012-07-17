package com.xfashion.client.cat.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.CategoryDTO;

public class MoveUpCategoryEvent extends FilterDataEvent<MoveUpCategoryHandler, CategoryDTO> {
	
	public static Type<MoveUpCategoryHandler> TYPE = new Type<MoveUpCategoryHandler>();

	protected int index;
	
	public MoveUpCategoryEvent(CategoryDTO cellData, int index) {
		super(cellData);
		this.index = index;
	}
	
	@Override
	public Type<MoveUpCategoryHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MoveUpCategoryHandler handler) {
		handler.onMoveUpCategory(this);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
