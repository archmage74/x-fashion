package com.xfashion.client.at.size.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.SizeDTO;

public class SelectSizeEvent extends FilterDataEvent<SelectSizeHandler, SizeDTO> {

	public static Type<SelectSizeHandler> TYPE = new Type<SelectSizeHandler>();
	
	public SelectSizeEvent(SizeDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<SelectSizeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SelectSizeHandler handler) {
		handler.onSelectSize(this);
	}

}
