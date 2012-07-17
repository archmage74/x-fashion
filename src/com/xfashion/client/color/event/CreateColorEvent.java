package com.xfashion.client.color.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.ColorDTO;

public class CreateColorEvent extends FilterDataEvent<CreateColorHandler, ColorDTO> {

	public static Type<CreateColorHandler> TYPE = new Type<CreateColorHandler>();
	
	public CreateColorEvent(ColorDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<CreateColorHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreateColorHandler handler) {
		handler.onCreateColor(this);
	}

}
