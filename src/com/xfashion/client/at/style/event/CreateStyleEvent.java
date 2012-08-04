package com.xfashion.client.at.style.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.StyleDTO;

public class CreateStyleEvent extends FilterDataEvent<CreateStyleHandler, StyleDTO> {

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
