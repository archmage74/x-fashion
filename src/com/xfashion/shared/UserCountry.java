package com.xfashion.shared;

public enum UserCountry {
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
