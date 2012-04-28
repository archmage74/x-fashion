package com.xfashion.client.at;

import com.google.gwt.i18n.client.Messages;
import com.google.gwt.i18n.client.LocalizableResource.Generate;

@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
public interface BarcodeMessages extends Messages {
	
	@DefaultMessage("{0,number,0}{1,number,00}{2,number,000}{3,number,000000}{4,number,0}")
	String ean(Integer type, Long category, Integer buyPrice, Long productNumber, Long parity);
	
}
