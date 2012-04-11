package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StyleDTO implements IsSerializable {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 

	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof StyleDTO)) return false;
		StyleDTO s = (StyleDTO) o;
		if (name == null) {
			if (s.getName() == null) {
				return true;
			} else {
				return false;
			}
		} else {
			if (name.equals(s.getName())) {
				return true;
			} else {
				return false;
			}
		}
	}

}
