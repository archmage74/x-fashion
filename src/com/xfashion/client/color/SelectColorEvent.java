package com.xfashion.client.color;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.ColorDTO;

public class SelectColorEvent extends FilterDataEvent2<SelectColorHandler, ColorDTO> {

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
