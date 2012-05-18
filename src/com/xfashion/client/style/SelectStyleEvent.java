package com.xfashion.client.style;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.StyleDTO;

public class SelectStyleEvent extends FilterDataEvent2<SelectStyleHandler, StyleDTO> {

	public static Type<SelectStyleHandler> TYPE = new Type<SelectStyleHandler>();
	
	public SelectStyleEvent(StyleDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<SelectStyleHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SelectStyleHandler handler) {
		handler.onSelectStyle(this);
	}

}
