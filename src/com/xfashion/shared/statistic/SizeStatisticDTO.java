package com.xfashion.shared.statistic;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SizeStatisticDTO implements IDetailStatistic, IsSerializable {

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
