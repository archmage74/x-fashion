package com.xfashion.client.at.size.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.SizeDTO;

public class ChooseSizeEvent extends FilterDataEvent<ChooseSizeHandler, SizeDTO> {

	public static Type<ChooseSizeHandler> TYPE = new Type<ChooseSizeHandler>();
	
	public ChooseSizeEvent(SizeDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<ChooseSizeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChooseSizeHandler handler) {
		handler.onChooseSize(this);
	}

}
