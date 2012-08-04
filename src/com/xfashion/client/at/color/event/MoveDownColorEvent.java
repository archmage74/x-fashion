package com.xfashion.client.at.color.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.ColorDTO;

public class MoveDownColorEvent extends FilterDataEvent<MoveDownColorHandler, ColorDTO> {
	
	public static Type<MoveDownColorHandler> TYPE = new Type<MoveDownColorHandler>();

	protected int index;
	
	public MoveDownColorEvent(ColorDTO cellData, int index) {
		super(cellData);
		this.index = index;
	}
	
	@Override
	public Type<MoveDownColorHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MoveDownColorHandler handler) {
		handler.onMoveDownColor(this);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
