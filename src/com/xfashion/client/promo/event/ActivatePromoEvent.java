package com.xfashion.client.promo.event;

import com.xfashion.shared.PromoDTO;

public class ActivatePromoEvent extends PromoEvent<ActivatePromoHandler> {

	public static Type<ActivatePromoHandler> TYPE = new Type<ActivatePromoHandler>();
	
	public ActivatePromoEvent(PromoDTO promo) {
		super(promo);
	}
	
	@Override
	public Type<ActivatePromoHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ActivatePromoHandler handler) {
		handler.onActivatePromo(this);
	}

}
