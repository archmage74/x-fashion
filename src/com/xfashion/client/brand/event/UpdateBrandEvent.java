package com.xfashion.client.brand.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.BrandDTO;

public class UpdateBrandEvent extends FilterDataEvent<UpdateBrandHandler, BrandDTO> {
	
	public static Type<UpdateBrandHandler> TYPE = new Type<UpdateBrandHandler>();

	public UpdateBrandEvent(BrandDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<UpdateBrandHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateBrandHandler handler) {
		handler.onUpdateBrand(this);
	}

}
