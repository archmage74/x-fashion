package com.xfashion.client.at.color.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.ColorDTO;

public class UpdateColorEvent extends FilterDataEvent<UpdateColorHandler, ColorDTO> {
	
	public static Type<UpdateColorHandler> TYPE = new Type<UpdateColorHandler>();

	public UpdateColorEvent(ColorDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<UpdateColorHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateColorHandler handler) {
		handler.onUpdateColor(this);
	}

}
