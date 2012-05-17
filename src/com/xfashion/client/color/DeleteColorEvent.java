package com.xfashion.client.color;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.ColorDTO;

public class DeleteColorEvent extends FilterDataEvent2<DeleteColorHandler, ColorDTO> {

	public static Type<DeleteColorHandler> TYPE = new Type<DeleteColorHandler>();
	
	public DeleteColorEvent(ColorDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<DeleteColorHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeleteColorHandler handler) {
		handler.onDeleteColor(this);
	}

}
