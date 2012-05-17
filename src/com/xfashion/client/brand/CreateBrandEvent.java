package com.xfashion.client.brand;

import com.xfashion.client.FilterDataEvent2;
import com.xfashion.shared.BrandDTO;

public class CreateBrandEvent extends FilterDataEvent2<CreateBrandHandler, BrandDTO> {

	public static Type<CreateBrandHandler> TYPE = new Type<CreateBrandHandler>();
	
	public CreateBrandEvent(BrandDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<CreateBrandHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreateBrandHandler handler) {
		handler.onCreateBrand(this);
	}

}
