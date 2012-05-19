package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SizeDTO extends FilterCellData implements IsSerializable {
	
	public SizeDTO() {
		super();
	}
	
	public SizeDTO(String name) {
		super();
		setName(name);
	}
	
}
