package com.xfashion.client.brand.event;

import com.xfashion.client.FilterDataEvent;
import com.xfashion.shared.BrandDTO;

public class DeleteBrandEvent extends FilterDataEvent<DeleteBrandHandler, BrandDTO> {

	public static Type<DeleteBrandHandler> TYPE = new Type<DeleteBrandHandler>();
	
	public DeleteBrandEvent(BrandDTO cellData) {
		super(cellData);
	}
	
	@Override
	public Type<DeleteBrandHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeleteBrandHandler handler) {
		handler.onDeleteBrand(this);
	}

}
