package com.xfashion.client.brand;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.BrandDTO;

public class SelectBrandEvent extends FilterDataEvent2<SelectBrandHandler, BrandDTO> {

	public static Type<SelectBrandHandler> TYPE = new Type<SelectBrandHandler>();
	
	public SelectBrandEvent(BrandDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<SelectBrandHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SelectBrandHandler handler) {
		handler.onSelectBrand(this);
	}

}
