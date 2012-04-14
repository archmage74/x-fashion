package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BrandDTO extends DTO implements IsSerializable {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 

	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof BrandDTO)) return false;
		BrandDTO s = (BrandDTO) o;
		boolean equal = true;
		equal &= attributeEquals(name, s.getName());
		return equal;
	}

}
