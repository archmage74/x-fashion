package com.xfashion.client.size.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.SizeDTO;

public class DeleteSizeEvent extends FilterDataEvent<DeleteSizeHandler, SizeDTO> {

	public static Type<DeleteSizeHandler> TYPE = new Type<DeleteSizeHandler>();
	
	public DeleteSizeEvent(SizeDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<DeleteSizeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeleteSizeHandler handler) {
		handler.onDeleteSize(this);
	}

}
