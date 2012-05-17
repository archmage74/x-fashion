package com.xfashion.client.size;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.SizeDTO;

public class MoveUpSizeEvent extends FilterDataEvent2<MoveUpSizeHandler, SizeDTO> {
	
	public static Type<MoveUpSizeHandler> TYPE = new Type<MoveUpSizeHandler>();

	protected int index;
	
	public MoveUpSizeEvent(SizeDTO cellData, int index) {
		super(cellData);
		this.index = index;
	}
	
	@Override
	public Type<MoveUpSizeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MoveUpSizeHandler handler) {
		handler.onMoveUpSize(this);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
