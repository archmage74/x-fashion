package com.xfashion.client.resources;

import com.google.gwt.i18n.client.Messages;

public interface Urls extends Messages {

	@DefaultMessage("/pdf/promosticker?promoKey={0}&amount={1,number}")
	String printPromoStickerUrl(String promoKey, int amount);
	
}
