package com.xfashion.client.at.render;

public class Prices {

	protected Integer pieces;
	
	protected Integer price;

	public Prices(Integer pieces, Integer price) {
		this.pieces = pieces;
		this.price = price;
	}

	public Integer getPieces() {
		return pieces;
	}

	public void setPieces(Integer pieces) {
		this.pieces = pieces;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
}