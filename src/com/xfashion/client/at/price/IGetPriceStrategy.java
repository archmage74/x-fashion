package com.xfashion.client.at.price;

public interface IGetPriceStrategy<T> {
	
	public Integer getPrice(T a);
}
