package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ColorDTO extends FilterCellData implements IsSerializable {
	
	public ColorDTO() {
		super();
	}
	
	public ColorDTO(String name) {
		super();
		setName(name);
	}

}
