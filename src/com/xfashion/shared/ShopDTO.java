package com.xfashion.shared;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ShopDTO implements IsSerializable, Serializable {

	private static final long serialVersionUID = 8206558764253102630L;

	private String keyString;

	private String name;

	private String description;

	public String getKeyString() {
		return keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
