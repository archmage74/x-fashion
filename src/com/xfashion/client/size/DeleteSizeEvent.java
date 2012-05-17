package com.xfashion.client.size;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.SizeDTO;

public class DeleteSizeEvent extends FilterDataEvent2<DeleteSizeHandler, SizeDTO> {

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
