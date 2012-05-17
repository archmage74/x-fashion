package com.xfashion.client.color;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.ColorDTO;

public class CreateColorEvent extends FilterDataEvent2<CreateColorHandler, ColorDTO> {

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
