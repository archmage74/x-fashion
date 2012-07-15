package com.xfashion.client.color;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.ColorDTO;

public class MoveUpColorEvent extends FilterDataEvent<MoveUpColorHandler, ColorDTO> {
	
	public static Type<MoveUpColorHandler> TYPE = new Type<MoveUpColorHandler>();

	protected int index;
	
	public MoveUpColorEvent(ColorDTO cellData, int index) {
		super(cellData);
		this.index = index;
	}
	
	@Override
	public Type<MoveUpColorHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MoveUpColorHandler handler) {
		handler.onMoveUpColor(this);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
