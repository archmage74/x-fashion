package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BrandDTO extends FilterCellData implements IsSerializable {
	
	public BrandDTO() {
		super();
	}
	
	public BrandDTO(String name) {
		super();
		setName(name);
	}

}
