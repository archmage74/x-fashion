package com.xfashion.client.style;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.StyleDTO;

public class CreateStyleEvent extends FilterDataEvent2<CreateStyleHandler, StyleDTO> {

	public static Type<CreateStyleHandler> TYPE = new Type<CreateStyleHandler>();
	
	public CreateStyleEvent(StyleDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<CreateStyleHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreateStyleHandler handler) {
		handler.onCreateStyle(this);
	}

}
