package com.xfashion.client.cat.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.CategoryDTO;

public class MoveDownCategoryEvent extends FilterDataEvent<MoveDownCategoryHandler, CategoryDTO> {
	
	public static Type<MoveDownCategoryHandler> TYPE = new Type<MoveDownCategoryHandler>();

	protected int index;
	
	public MoveDownCategoryEvent(CategoryDTO cellData, int index) {
		super(cellData);
		this.index = index;
	}
	
	@Override
	public Type<MoveDownCategoryHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MoveDownCategoryHandler handler) {
		handler.onMoveDownCategory(this);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
