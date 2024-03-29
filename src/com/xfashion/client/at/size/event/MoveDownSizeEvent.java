package com.xfashion.client.at.size.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.SizeDTO;

public class MoveDownSizeEvent extends FilterDataEvent<MoveDownSizeHandler, SizeDTO> {
	
	public static Type<MoveDownSizeHandler> TYPE = new Type<MoveDownSizeHandler>();

	protected int index;
	
	public MoveDownSizeEvent(SizeDTO cellData, int index) {
		super(cellData);
		this.index = index;
	}
	
	@Override
	public Type<MoveDownSizeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MoveDownSizeHandler handler) {
		handler.onMoveDownSize(this);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
