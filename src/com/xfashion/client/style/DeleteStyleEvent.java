package com.xfashion.client.style;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.StyleDTO;

public class DeleteStyleEvent extends FilterDataEvent2<DeleteStyleHandler, StyleDTO> {

	public static Type<DeleteStyleHandler> TYPE = new Type<DeleteStyleHandler>();
	
	public DeleteStyleEvent(StyleDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<DeleteStyleHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeleteStyleHandler handler) {
		handler.onDeleteStyle(this);
	}

}
