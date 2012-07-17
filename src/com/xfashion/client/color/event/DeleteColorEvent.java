package com.xfashion.client.color.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.ColorDTO;

public class DeleteColorEvent extends FilterDataEvent<DeleteColorHandler, ColorDTO> {

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
