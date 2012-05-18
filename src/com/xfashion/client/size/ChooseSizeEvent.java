package com.xfashion.client.size;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.SizeDTO;

public class ChooseSizeEvent extends FilterDataEvent2<ChooseSizeHandler, SizeDTO> {

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
