package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SizeStatisticDTO implements IsSerializable {

	private String size;
	
	private Integer pieces;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getPieces() {
		return pieces;
	}

	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

}
