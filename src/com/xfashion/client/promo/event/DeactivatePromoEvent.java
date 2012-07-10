package com.xfashion.client.promo.event;

import com.xfashion.shared.PromoDTO;

public class DeactivatePromoEvent extends PromoEvent<DeactivatePromoHandler> {

	public static Type<DeactivatePromoHandler> TYPE = new Type<DeactivatePromoHandler>();
	
	public DeactivatePromoEvent(PromoDTO promo) {
		super(promo);
	}
	
	@Override
	public Type<DeactivatePromoHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeactivatePromoHandler handler) {
		handler.onDeactivatePromo(this);
	}

}
