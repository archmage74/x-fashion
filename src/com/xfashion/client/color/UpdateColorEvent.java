package com.xfashion.client.color;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.ColorDTO;

public class UpdateColorEvent extends FilterDataEvent2<UpdateColorHandler, ColorDTO> {
	
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
