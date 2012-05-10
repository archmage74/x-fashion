package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BrandDTO extends FilterCellData<Long> implements IsSerializable {
	
	public static String ICON_PREFIX_BRAND = "brand";
	
	public BrandDTO() {
		setIconPrefix(ICON_PREFIX_BRAND);
	}
	
	public BrandDTO(String name, int sortIndex) {
		setName(name);
		setSortIndex(sortIndex);
		setIconPrefix(ICON_PREFIX_BRAND);
	}

	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof BrandDTO)) return false;
		BrandDTO s = (BrandDTO) o;
		boolean equal = true;
		equal &= attributeEquals(getName(), s.getName());
		equal &= attributeEquals(getSortIndex(), s.getSortIndex());
		return equal;
	}

}
