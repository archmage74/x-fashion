package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class DTO<T extends Comparable<T>> implements IsSerializable {

	protected T id;
	
	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

	protected boolean attributeEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null) {
			return false;
		}
		return o1.equals(o2);
	}

}
