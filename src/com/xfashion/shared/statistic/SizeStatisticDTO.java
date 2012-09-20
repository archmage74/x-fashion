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

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SizeStatisticDTO { ");
		sb.append("size=").append(size).append(", ");
		sb.append("pieces=").append(pieces);
		sb.append(" }");
		return sb.toString();
	}
}
