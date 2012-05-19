package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StyleDTO extends FilterCellData implements IsSerializable {

	public StyleDTO() {
		super();
	}

	public StyleDTO(String name) {
		super();
		setName(name);
	}

}
