package com.xfashion.client.color.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.ColorDTO;

public class SelectColorEvent extends FilterDataEvent<SelectColorHandler, ColorDTO> {

	public static Type<SelectColorHandler> TYPE = new Type<SelectColorHandler>();
	
	public SelectColorEvent(ColorDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<SelectColorHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SelectColorHandler handler) {
		handler.onSelectColor(this);
	}

}
