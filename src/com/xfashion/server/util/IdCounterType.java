package com.xfashion.server.util;

public enum IdCounterType {
	DELIVERY_NOTICE (200000000001L);
	
	private final Long startValue;

	IdCounterType(Long startValue) {
		this.startValue = startValue;
	}
	
	Long startValue() {
		return startValue;
	}
}
