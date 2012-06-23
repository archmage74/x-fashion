package com.xfashion.shared;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ShopDTO implements IsSerializable, Serializable {

	private static final long serialVersionUID = 8206558764253102630L;

	private String keyString;

	private String shortName;
	
	private String name;
	
	private String street;
	
	private String housenumber;
	
	private String postalcode;
	
	private String city;

	public String getKeyString() {
		return keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHousenumber() {
		return housenumber;
	}

	public void setHousenumber(String housenumber) {
		this.housenumber = housenumber;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
