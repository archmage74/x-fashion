package com.xfashion.client.at.style.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.StyleDTO;

public class UpdateStyleEvent extends FilterDataEvent<UpdateStyleHandler, StyleDTO> {
	
	public static Type<UpdateStyleHandler> TYPE = new Type<UpdateStyleHandler>();

	public UpdateStyleEvent(StyleDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<UpdateStyleHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateStyleHandler handler) {
		handler.onUpdateStyle(this);
	}

}
