package com.xfashion.client.promo.event;

import com.google.web.bindery.event.shared.Event;
import com.xfashion.shared.PromoDTO;

public abstract class PromoEvent<T> extends Event<T> {

	protected PromoDTO promo;

	public PromoEvent(PromoDTO promo) {
		super();
		this.promo = promo;
	}

	public PromoDTO getPromo() {
		return promo;
	}

}