package com.xfashion.client.promo.event;

import com.xfashion.shared.PromoDTO;

public class PrintPromosEvent extends PromoEvent<PrintPromosHandler> {

	public static Type<PrintPromosHandler> TYPE = new Type<PrintPromosHandler>();
	
	/** amount of promo-barcode-stickers to print */ 
	private Integer amount;
	
	public PrintPromosEvent(PromoDTO promo, Integer amount) {
		super(promo);
		this.amount = amount;
	}
	
	public Integer getAmount() {
		return amount;
	}

	@Override
	public Type<PrintPromosHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PrintPromosHandler handler) {
		handler.onPrintPromos(this);
	}

}
