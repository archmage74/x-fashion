package com.xfashion.client.brand;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.BrandDTO;

public class ChooseBrandEvent extends FilterDataEvent2<ChooseBrandHandler, BrandDTO> {

	public static Type<ChooseBrandHandler> TYPE = new Type<ChooseBrandHandler>();
	
	public ChooseBrandEvent(BrandDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<ChooseBrandHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChooseBrandHandler handler) {
		handler.onChooseBrand(this);
	}

}
