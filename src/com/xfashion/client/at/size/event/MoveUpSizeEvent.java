package com.xfashion.client.at.size.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.SizeDTO;

public class MoveUpSizeEvent extends FilterDataEvent<MoveUpSizeHandler, SizeDTO> {
	
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
