package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SizeDTO extends DTO implements IsSerializable {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 

	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof SizeDTO)) return false;
		SizeDTO s = (SizeDTO) o;
		boolean equal = true;
		equal &= attributeEquals(name, s.getName());
		return equal;
	}

}
