package com.xfashion.client.at.brand.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.BrandDTO;

public class MoveDownBrandEvent extends FilterDataEvent<MoveDownBrandHandler, BrandDTO> {
	
	public static Type<MoveDownBrandHandler> TYPE = new Type<MoveDownBrandHandler>();

	protected int index;
	
	public MoveDownBrandEvent(BrandDTO cellData, int index) {
		super(cellData);
		this.index = index;
	}
	
	@Override
	public Type<MoveDownBrandHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MoveDownBrandHandler handler) {
		handler.onMoveDownBrand(this);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
