package com.xfashion.client.size;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.SizeDTO;

public class UpdateSizeEvent extends FilterDataEvent<UpdateSizeHandler, SizeDTO> {
	
	public static Type<UpdateSizeHandler> TYPE = new Type<UpdateSizeHandler>();

	public UpdateSizeEvent() {
		super(null);
	}
	
	public UpdateSizeEvent(SizeDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<UpdateSizeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateSizeHandler handler) {
		handler.onUpdateSize(this);
	}

}
