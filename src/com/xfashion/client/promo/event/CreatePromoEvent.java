package com.xfashion.client.promo.event;

import com.xfashion.shared.PromoDTO;

public class CreatePromoEvent extends PromoEvent<CreatePromoHandler> {

	public static Type<CreatePromoHandler> TYPE = new Type<CreatePromoHandler>();
	
	public CreatePromoEvent(PromoDTO promo) {
		super(promo);
	}
	
	@Override
	public Type<CreatePromoHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreatePromoHandler handler) {
		handler.onCreatePromo(this);
	}

}
