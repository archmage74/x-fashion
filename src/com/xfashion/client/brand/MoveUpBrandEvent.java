package com.xfashion.client.brand;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.BrandDTO;

public class MoveUpBrandEvent extends FilterDataEvent<MoveUpBrandHandler, BrandDTO> {
	
	public static Type<MoveUpBrandHandler> TYPE = new Type<MoveUpBrandHandler>();

	protected int index;
	
	public MoveUpBrandEvent(BrandDTO cellData, int index) {
		super(cellData);
		this.index = index;
	}
	
	@Override
	public Type<MoveUpBrandHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MoveUpBrandHandler handler) {
		handler.onMoveUpBrand(this);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
