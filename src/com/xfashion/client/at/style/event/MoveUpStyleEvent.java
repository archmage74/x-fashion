package com.xfashion.client.at.style.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.StyleDTO;

public class MoveUpStyleEvent extends FilterDataEvent<MoveUpStyleHandler, StyleDTO> {
	
	public static Type<MoveUpStyleHandler> TYPE = new Type<MoveUpStyleHandler>();

	protected int index;
	
	public MoveUpStyleEvent(StyleDTO cellData, int index) {
		super(cellData);
		this.index = index;
	}
	
	@Override
	public Type<MoveUpStyleHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MoveUpStyleHandler handler) {
		handler.onMoveUpStyle(this);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
