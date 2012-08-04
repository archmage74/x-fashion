package com.xfashion.client.at.name;

import com.google.web.bindery.event.shared.Event;

public class NameFilterEvent extends Event<NameFilterHandler> {
	
	public static Type<NameFilterHandler> TYPE = new Type<NameFilterHandler>();

	String name;
	
	public NameFilterEvent(String name) {
		this.name = name;
	}
	
	@Override
	public Type<NameFilterHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(NameFilterHandler handler) {
		handler.onNameFilter(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
