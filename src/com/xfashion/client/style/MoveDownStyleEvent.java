package com.xfashion.client.style;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.StyleDTO;

public class MoveDownStyleEvent extends FilterDataEvent<MoveDownStyleHandler, StyleDTO> {
	
	public static Type<MoveDownStyleHandler> TYPE = new Type<MoveDownStyleHandler>();

	protected int index;
	
	public MoveDownStyleEvent(StyleDTO cellData, int index) {
		super(cellData);
		this.index = index;
	}
	
	@Override
	public Type<MoveDownStyleHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MoveDownStyleHandler handler) {
		handler.onMoveDownStyle(this);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
