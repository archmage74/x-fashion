package com.xfashion.client;

public interface ICrud<T> {
	
	void delete(T item);
	
	void edit(T item);

	void moveUp(T item);
	
	void moveDown(T item);
	
	void update(T item);
}
