package com.xfashion.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class DownClickHandler<T> implements ClickHandler {
	
	private T item;
	private ICrud<T> crud;
	
	public DownClickHandler(T item, ICrud<T> crud) {
		this.item = item;
		this.crud = crud;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		crud.moveDown(item);
	}
	
}
