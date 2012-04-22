package com.xfashion.client.tool;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.xfashion.client.ICrud;

public class UpClickHandler<T> implements ClickHandler {
	
	private T item;
	private ICrud<T> crud;
	
	public UpClickHandler(T item, ICrud<T> crud) {
		this.item = item;
		this.crud = crud;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		crud.moveUp(item);
	}
	
}
