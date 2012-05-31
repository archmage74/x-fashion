package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum UserCountry implements IsSerializable {
	AT ("Österreich"),
	DE ("Deutschland");
	
	private final String longName;
	
	UserCountry(String longName) {
		this.longName = longName;
	}
	
	public String longName() {
		return longName;
	}
}
