package com.xfashion.client.size;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.SizeDTO;

public class CreateSizeEvent extends FilterDataEvent<CreateSizeHandler, SizeDTO> {

	public static Type<CreateSizeHandler> TYPE = new Type<CreateSizeHandler>();
	
	public CreateSizeEvent(SizeDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<CreateSizeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreateSizeHandler handler) {
		handler.onCreateSize(this);
	}

}
