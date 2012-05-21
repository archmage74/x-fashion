package com.xfashion.client.color;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.ColorDTO;

public class ChooseColorEvent extends FilterDataEvent2<ChooseColorHandler, ColorDTO> {

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