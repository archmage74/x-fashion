package com.xfashion.client.tool;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.ICrud;
import com.xfashion.shared.FilterCellData;

public class DeleteClickHandler<T extends FilterCellData<?>> implements ClickHandler {
	
	private T item;
	private ICrud<T> crud;
	
	public DeleteClickHandler(T item, FilterPanel<T> crud) {
		this.item = item;
		this.crud = crud;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		crud.delete(item);
	}
	
}
