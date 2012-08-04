package com.xfashion.client.at.color.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.ColorDTO;

public class ChooseColorEvent extends FilterDataEvent<ChooseColorHandler, ColorDTO> {

	public static Type<ChooseColorHandler> TYPE = new Type<ChooseColorHandler>();
	
	public ChooseColorEvent(ColorDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<ChooseColorHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChooseColorHandler handler) {
		handler.onChooseColor(this);
	}

}
