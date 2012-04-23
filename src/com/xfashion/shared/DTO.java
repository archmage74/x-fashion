package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class DTO implements IsSerializable {

	protected Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
